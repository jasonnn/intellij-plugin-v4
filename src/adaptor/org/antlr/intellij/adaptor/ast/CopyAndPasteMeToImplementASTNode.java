package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLock;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/24/15.
 *
 * Copy-and-paste mixin template for antlr nodes that want to implement ASTNode
 */
public abstract class CopyAndPasteMeToImplementASTNode implements AntlrAST {


    int siblingIndex = -1;

    PsiElement psiElement = null;

    @Override
    public PsiElement getPsi() {
        PsiElement element = psiElement;
        if (element != null) return element;

        synchronized (PsiLock.LOCK) {
            element = psiElement;
            if (element != null) return element;

            element = AntlrASTSupport.getPsi(this);
            psiElement = element;
            return element;
        }
    }

    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        return clazz.cast(getPsi());
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


    IElementType elementType;
    private final UserDataHolderBase dataHolder = new UserDataHolderBase();

    @Override
    @Nullable
    public <T> T getUserData(@NotNull Key<T> key) {
        return dataHolder.getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, T value) {
        dataHolder.putUserData(key, value);
    }

    @NotNull
    @Override
    public IElementType getElementType() {
        return elementType;
    }

    @NotNull
    @Override
    public CharSequence getChars() {
        return AntlrASTSupport.getChars(this);
    }

    @Override
    public boolean textContains(char c) {
        return AntlrASTSupport.textContains(this, c);
    }

    @Override
    public int getStartOffset() {
        return AntlrASTSupport.getStartOffset(this);
    }

    @Override
    public int getTextLength() {
        return AntlrASTSupport.getTextLength(this);
    }

    @Override
    public TextRange getTextRange() {
        return AntlrASTSupport.getTextRange(this);
    }

    @Override
    public ASTNode getTreeParent() {
        return AntlrASTSupport.getTreeParent(this);
    }

    @Override
    public ASTNode getFirstChildNode() {
        return AntlrASTSupport.getFirstChildNode(this);
    }

    @Override
    public ASTNode getLastChildNode() {
        return AntlrASTSupport.getLastChildNode(this);
    }

    @Override
    public ASTNode getTreeNext() {
        return AntlrASTSupport.getTreeNext(this);
    }

    @Override
    public ASTNode getTreePrev() {
        return AntlrASTSupport.getTreePrev(this);
    }

    @NotNull
    @Override
    public ASTNode[] getChildren(@Nullable TokenSet filter) {
        return AntlrASTSupport.getChildren(this, filter);
    }

    @Override
    public void addChild(@NotNull ASTNode child) {
        AntlrASTSupport.addChild(this, child);
    }

    @Override
    public void addChild(@NotNull ASTNode child, @Nullable ASTNode anchorBefore) {
        AntlrASTSupport.addChild(this, child, anchorBefore);
    }

    @Override
    public void addLeaf(@NotNull IElementType leafType, CharSequence leafText, @Nullable ASTNode anchorBefore) {
        AntlrASTSupport.addLeaf(this, leafType, leafText, anchorBefore);
    }

    @Override
    public void removeChild(@NotNull ASTNode child) {
        AntlrASTSupport.removeChild(this, child);
    }

    @Override
    public void removeRange(@NotNull ASTNode firstNodeToRemove, ASTNode firstNodeToKeep) {
        AntlrASTSupport.removeRange(this, firstNodeToRemove, firstNodeToKeep);
    }

    @Override
    public void replaceChild(@NotNull ASTNode oldChild, @NotNull ASTNode newChild) {
        AntlrASTSupport.replaceChild(this, oldChild, newChild);
    }

    @Override
    public void replaceAllChildrenToChildrenOf(ASTNode anotherParent) {
        AntlrASTSupport.replaceAllChildrenToChildrenOf(this, anotherParent);
    }

    @Override
    public void addChildren(ASTNode firstChild, ASTNode firstChildToNotAdd, ASTNode anchorBefore) {
        AntlrASTSupport.addChildren(this, firstChild, firstChildToNotAdd, anchorBefore);
    }

    @Override
    public ASTNode copyElement() {
        return AntlrASTSupport.copyElement(this);
    }

    @Nullable
    @Override
    public ASTNode findLeafElementAt(int offset) {
        return AntlrASTSupport.findLeafElementAt(this, offset);
    }

    @Nullable
    @Override
    public <T> T getCopyableUserData(@NotNull Key<T> key) {
        return AntlrASTSupport.getCopyableUserData(this, key);
    }

    @Override
    public <T> void putCopyableUserData(@NotNull Key<T> key, T value) {
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


    @NotNull
    @Override
    public Object clone() {
        return AntlrASTSupport.handleClone(this);
    }


}