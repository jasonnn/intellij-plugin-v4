package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.openapi.util.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.CharTableImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.CharTable;
import com.intellij.util.text.ImmutableCharSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/24/15.
 */
public class MyAntlrFileNode implements FileASTNode, Getter<FileASTNode> {

    private volatile CharTable myCharTable = new CharTableImpl();
    private volatile boolean myDetached;

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

        }
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

    private CharSequence myText() {
        synchronized (lock) {
            return myText;
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
        //TODO double checked locking
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

    @Override
    public String getText() {
        return grammarRoot.getText();
    }

    @Override
    public CharSequence getChars() {
        return grammarRoot.getChars();
    }

    @Override
    public boolean textContains(char c) {
       return grammarRoot.textContains(c);
    }

    @Override
    public int getStartOffset() {
        return 0;
    }

    @Override
    public int getTextLength() {
        return grammarRoot.getTextLength();
    }

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
        return grammarRoot;
    }

    @Override
    public ASTNode getLastChildNode() {
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
}
