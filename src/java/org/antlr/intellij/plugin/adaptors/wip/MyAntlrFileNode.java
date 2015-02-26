package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.LogUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.impl.source.CharTableImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.ILazyParseableElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.CharTable;
import com.intellij.util.text.ImmutableCharSequence;
import com.intellij.util.text.StringFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

/**
 * Created by jason on 2/24/15.
 */
public class MyAntlrFileNode implements FileASTNode, Getter<FileASTNode> {


    private static final Logger LOG = Logger.getInstance("#org.antlr.intellij.plugin.adaptors.wip.MyAntlrFileNode");



    private volatile CharTable myCharTable = new CharTableImpl();
    private volatile boolean myDetached;
    private volatile int myCachedLength = -1;


    AntlrAST grammarRoot;

    PsiElement wrapper = null;

    IElementType elementType;

    private final UserDataHolder dataHolder = new UserDataHolderBase();


    private CharSequence myText;
    private final Object lock = new Object();

    public MyAntlrFileNode(@NotNull IElementType type, @Nullable CharSequence text) {
        this.elementType = type;
        if (text != null) {
            synchronized (lock) {
                myText = ImmutableCharSequence.asImmutable(text);
            }
            setCachedLength(text.length());
        }
        putUserData(CharTable.CHAR_TABLE_KEY, myCharTable);
    }


    public MyAntlrFileNode(AntlrAST grammarRoot, IElementType elementType) {
        this.grammarRoot = grammarRoot;
        this.elementType = elementType;
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
        return null;
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
        if (grammarRoot != null) {
            return grammarRoot.getText().toCharArray();
        }
        throw new UnsupportedOperationException();
        // int startStamp = myModificationsCount;

        // final int len = getTextLength();
        // return getText().toCharArray();

//        if (startStamp != myModificationsCount) {
//            throw new AssertionError(
//                    "Tree changed while calculating text. startStamp:"+startStamp+
//                            "; current:"+myModificationsCount+
//                            "; myHC:"+myHC+
//                            "; assertThreading:"+ASSERT_THREADING+
//                            "; this: " + this +
//                            "\n" + getThreadingDiagnostics());
//        }

//        char[] buffer = new char[len];
//        final int endOffset;
//        try {
//            endOffset = AstBufferUtil.toBuffer(this, buffer, 0);
//        }
//        catch (ArrayIndexOutOfBoundsException e) {
//            @NonNls String msg = "Underestimated text length: " + len;
//           // msg += diagnoseTextInconsistency(new String(buffer), startStamp);
//            try {
//                int length = AstBufferUtil.toBuffer(this, new char[len], 0);
//                msg += ";\n repetition gives success (" + length + ")";
//            }
//            catch (ArrayIndexOutOfBoundsException e1) {
//                msg += ";\n repetition fails as well";
//            }
//            throw new RuntimeException(msg, e);
//        }
//        if (endOffset != len) {
//            @NonNls String msg = "len=" + len + ";\n endOffset=" + endOffset;
//           // msg += diagnoseTextInconsistency(new String(buffer, 0, Math.min(len, endOffset)), startStamp);
//            throw new AssertionError(msg);
//        }
//        return buffer;
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
        CharSequence text = myText();
        if (text != null) {
            return text.length();
        }
        if (grammarRoot != null) {
            return grammarRoot.getTextLength();
        }
        throw new UnsupportedOperationException("TODO");
        //return super.getTextLength();
    }


    public int getNotCachedLength() {
        CharSequence text = myText();
        if (text != null) {
            return text.length();
        }
        throw new UnsupportedOperationException("TODO");

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
        PsiElement psi = wrapper;
        if (psi == null) {
            psi = wrapper = AntlrASTSupport.getPsi(this);
        }
        return psi;
    }

    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        return clazz.cast(getPsi());
    }

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
       return grammarRoot.textContains(c);
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
        return grammarRoot.getTextRange();
    }

    @Override
    public ASTNode getTreeParent() {
        return null;
    }

    @Override
    public ASTNode getFirstChildNode() {
        ensureParsed();
        return grammarRoot;
    }

    @Override
    public ASTNode getLastChildNode() {
        ensureParsed();
        return grammarRoot;
    }

    @Override
    public ASTNode getTreeNext() {
        return null;
    }

    @Override
    public ASTNode getTreePrev() {
       return null;
    }

    @Override
    public ASTNode[] getChildren(@Nullable TokenSet filter) {
        //TODO
        return new ASTNode[]{grammarRoot};
    }

    @Override
    public void addChild(@NotNull ASTNode child) {
        this.grammarRoot= (AntlrAST) child;
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
        //TODO
        return grammarRoot;
    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type, @Nullable ASTNode anchor) {
        return grammarRoot;
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet) {
        return grammarRoot;
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet, @Nullable ASTNode anchor) {
        return grammarRoot;
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
        this.wrapper = psiFile;
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
}
