package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.LogUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLock;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.impl.source.CharTableImpl;
import com.intellij.psi.tree.*;
import com.intellij.util.CharTable;
import com.intellij.util.text.ImmutableCharSequence;
import com.intellij.util.text.StringFactory;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.List;

/**
 * Created by jason on 2/24/15.
 * <p/>
 * We have to implement this ourselves because the one intellij provides does a lot of casting of ASTNodes
 * to concrete types.
 *
 * @see com.intellij.psi.impl.source.tree.FileElement
 */

//TODO see if this is whats causing ideas indexing system to go nuts.
//TODO: some of these methods are probably implemented incorrectly. Many are implemented poorly.
public class AntlrFileNode extends ParserRuleContext implements FileASTNode, Getter<FileASTNode>,AntlrAST {


    private static final Logger LOG = Logger.getInstance("#org.antlr.intellij.adaptor.ast.AntlrFileNode");


    private volatile CharTable myCharTable = new CharTableImpl();
    private volatile boolean myDetached;
    private volatile int myCachedLength = -1;

    int siblingIndex=-1;


    // AntlrAST grammarRoot;

    PsiElement psi = null;

    IElementType elementType;

    private final UserDataHolder dataHolder = new UserDataHolderBase();


    private CharSequence myText;
    private final Object lock = new Object();

    public AntlrFileNode(@NotNull IElementType type, @Nullable CharSequence text) {
        this.elementType = type;
        if (text != null) {
            synchronized (lock) {
                myText = ImmutableCharSequence.asImmutable(text);
            }
            setCachedLength(text.length());
        }
        putUserData(CharTable.CHAR_TABLE_KEY, myCharTable);
    }


    public AntlrFileNode(AntlrAST grammarRoot, IElementType elementType) {
        this.elementType = elementType;
        setRoot(grammarRoot);
    }

    public void setRoot(AntlrAST node) {
        AntlrASTSupport.copyNodeAndReparentChildren((ParserRuleContext) node, this);
    }

    @NotNull
    @Override
    public CharTable getCharTable() {
        return myCharTable;
    }

    public boolean isParsed() {
        return myText() == null;
    }

    @Nullable
    @Override
    public LighterAST getLighterAST() {
        final IFileElementType contentType = (IFileElementType) getElementType();
        assert contentType instanceof ILightStubFileElementType : contentType;

        LighterAST tree;
        if (!isParsed()) {
            return new FCTSBackedLighterAST(getCharTable(), ((ILightStubFileElementType<?>) contentType).parseContentsLight(this));
        } else {
            tree = new TreeBackedLighterAST(this);
        }
        return tree;
    }

    private CharSequence myText() {
        synchronized (lock) {
            return myText;
        }
    }

    public void clearCaches() {
        // super.clearCaches();
        synchronized (lock) {
            if (myText != null) {
                setCachedLength(myText.length());
            }
        }
    }

    String defaultGetText() {
        return StringFactory.createShared(textToCharArray());
    }

    public char[] textToCharArray() {
        ApplicationManager.getApplication().assertReadAccessAllowed();
        return super.getText().toCharArray();

    }


    @NotNull
    @Override
    public String getText() {
        CharSequence text = myText();
        if (text != null) {
            return text.toString();
        }
        return defaultGetText();
    }

    @Override
    @NotNull
    public CharSequence getChars() {
        CharSequence text = myText();
        if (text != null) {
            return text;
        }
        return defaultGetText();
    }

    @Override
    public int getTextLength() {
        return getTextRange().getLength();
//        CharSequence text = myText();
//        if (text != null) {
//            return text.length();
//        }
//        if (grammarRoot != null) {
//            return grammarRoot.getTextLength();
//        }
//        throw new UnsupportedOperationException("TODO");
        //return super.getTextLength();
    }


    public int getNotCachedLength() {
        return -1;
//        CharSequence text = myText();
//        if (text != null) {
//            return text.length();
//        }
//        throw new UnsupportedOperationException("TODO");

        //return super.getNotCachedLength();
    }

    private void ensureParsed() {
        if (!ourParsingAllowed) {
            LOG.error("Parsing not allowed!!!");
        }
        CharSequence text = myText();
        if (text == null) return;

//        if (TreeUtil.getFileElement(this) == null) {
//            LOG.error("Chameleons must not be parsed till they're in file tree: " + this);
//        }

        ApplicationManager.getApplication().assertReadAccessAllowed();

        DebugUtil.startPsiModification("lazy-parsing");
        try {
            ILazyParseableElementType type = (ILazyParseableElementType) getElementType();
            ASTNode parsedNode = type.parseContents(this);

            if (parsedNode == null && text.length() > 0) {
                CharSequence diagText = ApplicationManager.getApplication().isInternal() ? text : "";
                LOG.error("No parse for a non-empty string: " + diagText + "; type=" + LogUtil.objectAndClass(type));
            }

            synchronized (lock) {
                if (myText == null) return;
//                if (rawFirstChild() != null) {
//                    LOG.error("Reentrant parsing?");
//                }

                myText = null;

                if (parsedNode == null) return;
                addChild(parsedNode);
                //super.rawAddChildrenWithoutNotifications((TreeElement)parsedNode);
            }
        } finally {
            DebugUtil.finishPsiModification();
        }

        if (!Boolean.TRUE.equals(ourSuppressEagerPsiCreation.get())) {
            // create PSI all at once, to reduce contention of PsiLock in CompositeElement.getPsi()
            // create PSI outside the 'lock' since this method grabs PSI_LOCK and deadlock is possible when someone else locks in the other order.
            //createAllChildrenPsiIfNecessary();
        }
    }


    @Override
    @Nullable
    public <T> T getUserData(@NotNull Key<T> key) {
        return dataHolder.getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, T value) {
        dataHolder.putUserData(key, value);
    }


    @Override
    public PsiElement getPsi() {
        PsiElement psi = this.psi;
        if (psi == null) {
            psi = this.psi = AntlrASTSupport.getPsi(this);
        }
        return psi;
    }

    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        return clazz.cast(getPsi());
    }

    @NotNull
    @Override
    public IElementType getElementType() {
        return elementType;
    }

//    @Override
//    public String getText() {
//        return grammarRoot.getText();
//    }
//
//    @Override
//    public CharSequence getChars() {
//        return grammarRoot.getChars();
//    }

    @Override
    public boolean textContains(char c) {
       return AntlrASTSupport.textContains(this,c);
    }

    @Override
    public int getStartOffset() {
        return 0;
    }

//    @Override
//    public int getTextLength() {
//        return grammarRoot.getTextLength();
//    }

    @Override
    public TextRange getTextRange() {
        return AntlrASTSupport.getTextRange(this);
    }

    @Override
    public ASTNode getTreeParent() {
        return null;
    }

    @Override
    public ASTNode getFirstChildNode() {
        ensureParsed();
        return AntlrASTSupport.getFirstChildNode(this);
    }

    @Override
    public ASTNode getLastChildNode() {
        ensureParsed();
       return AntlrASTSupport.getLastChildNode(this);
    }

    @Override
    public ASTNode getTreeNext() {
        return null;
    }

    @Override
    public ASTNode getTreePrev() {
        return null;
    }

    @NotNull
    @Override
    public ASTNode[] getChildren(@Nullable TokenSet filter) {
        return AntlrASTSupport.getChildren(this, filter);
    }

    @Override
    public void addChild(@NotNull ASTNode child) {
        setRoot((AntlrAST) child);
    }

    @Override
    public void addChild(@NotNull ASTNode child, @Nullable ASTNode anchorBefore) {
        addChild(child);
    }

    @Override
    public void addLeaf(@NotNull IElementType leafType, CharSequence leafText, @Nullable ASTNode anchorBefore) {
        throw new UnsupportedOperationException("should not contain leafs");
    }

    @Override
    public void removeChild(@NotNull ASTNode child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeRange(@NotNull ASTNode firstNodeToRemove, ASTNode firstNodeToKeep) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceChild(@NotNull ASTNode oldChild, @NotNull ASTNode newChild) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAllChildrenToChildrenOf(ASTNode anotherParent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addChildren(ASTNode firstChild, ASTNode firstChildToNotAdd, ASTNode anchorBefore) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ASTNode copyElement() {
        throw new UnsupportedOperationException("TODO!!!");
    }

    @Nullable
    @Override
    public ASTNode findLeafElementAt(int offset) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public <T> T getCopyableUserData(Key<T> key) {
        return AntlrASTSupport.getCopyableUserData(this, key);
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, T value) {
        AntlrASTSupport.putCopyableUserData(this, key, value);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type) {
        return AntlrASTSupport.findChildByType(this, type);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type, @Nullable ASTNode anchor) {
        return AntlrASTSupport.findChildByType(this, type, anchor);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet) {
        return AntlrASTSupport.findChildByType(this, typesSet);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet, @Nullable ASTNode anchor) {
        return AntlrASTSupport.findChildByType(this, typesSet, anchor);
    }


    @Override
    public Object clone() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public FileASTNode get() {
        return this;
    }

    public void setPsi(PsiFile psiFile) {
        this.psi = psiFile;
    }


    private static boolean ourParsingAllowed = true;

    @TestOnly
    public static void setParsingAllowed(boolean allowed) {
        ourParsingAllowed = allowed;
    }

    public static void setSuppressEagerPsiCreation(boolean suppress) {
        ourSuppressEagerPsiCreation.set(suppress);
    }

    private static final ThreadLocal<Boolean> ourSuppressEagerPsiCreation = new ThreadLocal<Boolean>();

    public void setCachedLength(int cachedLength) {
        this.myCachedLength = cachedLength;
    }

    public int getCachedLength() {
        return myCachedLength;
    }

    public void setMyCachedLength(int myCachedLength) {
        this.myCachedLength = myCachedLength;
    }

    @NonNls
    public String toString() {
        return "ANTLRv4 File Node" + "(" + getElementType().toString() + ")";
    }


    @Override
    public int getSiblingIndex() {
        int index = siblingIndex;
        if (index != -1) return index;

        synchronized (PsiLock.LOCK) {
            index = siblingIndex;
            if (index != -1) return index;

            index = AntlrASTSupport.getSiblingIndex(this);
            siblingIndex = index;
            return index;
        }
    }
}
