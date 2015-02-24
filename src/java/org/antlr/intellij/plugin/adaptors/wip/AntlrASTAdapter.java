package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/23/15.
 */
public class AntlrASTAdapter extends UserDataHolderBase implements ASTNode {
    protected final IElementType type;
    protected final ParseTree tree;
    protected int siblingIndex = -1;

    public AntlrASTAdapter(IElementType myType, ParseTree tree) {
        this.type = myType;
        this.tree = tree;
    }

    public int getSiblingIndex() {
        int index = siblingIndex;
        if (index == -1) {
            ParseTree parent = tree.getParent();
            if (parent == null) {
                index = 0;
            } else {
                for (int i = 0; i < parent.getChildCount(); i++) {
                    ParseTree child = parent.getChild(i);
                    if (child == this) {
                        index = i;
                        break;
                    }
                }
            }
            assert index != -1;
            siblingIndex = index;
        }
        return index;
    }

    @Override
    public IElementType getElementType() {
        return type;
    }

    @Override
    public String getText() {
        return tree.getText();
    }

    @Override
    public CharSequence getChars() {
        return getText();
    }

    @Override
    public boolean textContains(char c) {
        CharSequence chars = getChars();
        for (int i = 0; i < chars.length(); i++) {
            if (chars.charAt(i) == c) return true;
        }
        return false;
    }

    @Override
    public int getStartOffset() {
        //TODO probably not correct;
        return tree.getSourceInterval().a;
    }

    @Override
    public int getTextLength() {
        return getChars().length();
    }

    @Override
    public TextRange getTextRange() {
        return TextRange.from(getStartOffset(), getTextLength());
    }

    @Override
    public ASTNode getTreeParent() {
        ParseTree parent = tree.getParent();
        return parent == null ? null : (ASTNode) parent;
    }

    @Override
    public ASTNode getFirstChildNode() {
        ParseTree child = tree.getChildCount() > 0 ? tree.getChild(0) : null;
        return (ASTNode) child;
    }

    @Override
    public ASTNode getLastChildNode() {
        int childCount = tree.getChildCount();
        ParseTree child = childCount > 0 ? tree.getChild(childCount - 1) : null;
        return (ASTNode) child;
    }

    @Override
    public ASTNode getTreeNext() {
        ParseTree parent = tree.getParent();
        if (parent == null) return null;
        int nextIndex = getSiblingIndex() + 1;
        if (parent.getChildCount() <= nextIndex) return null;
        return (ASTNode) parent.getChild(nextIndex);

    }

    @Override
    public ASTNode getTreePrev() {
        ParseTree parent = tree.getParent();
        if (parent == null) return null;
        int nextIndex = getSiblingIndex() - 1;
        if (nextIndex <= 0) return null;
        return (ASTNode) parent.getChild(nextIndex);
    }

    @Override
    public ASTNode[] getChildren(TokenSet filter) {
        throw new UnsupportedOperationException("todo!!");
    }

    @Override
    public void addChild(@NotNull ASTNode child) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public void addChild(@NotNull ASTNode child, ASTNode anchorBefore) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public void addLeaf(@NotNull IElementType leafType, CharSequence leafText, ASTNode anchorBefore) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public void removeChild(@NotNull ASTNode child) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public void removeRange(@NotNull ASTNode firstNodeToRemove, ASTNode firstNodeToKeep) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public void replaceChild(@NotNull ASTNode oldChild, @NotNull ASTNode newChild) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public void replaceAllChildrenToChildrenOf(ASTNode anotherParent) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public void addChildren(ASTNode firstChild, ASTNode firstChildToNotAdd, ASTNode anchorBefore) {
        throw new UnsupportedOperationException("todo!!");

    }

    @Override
    public ASTNode copyElement() {
        throw new UnsupportedOperationException("todo!!");
    }

    @Nullable
    @Override
    public ASTNode findLeafElementAt(int offset) {
        throw new UnsupportedOperationException("todo!!");
    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type) {
        throw new UnsupportedOperationException("todo!!");
    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type, @Nullable ASTNode anchor) {
        throw new UnsupportedOperationException("todo!!");
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet) {
        throw new UnsupportedOperationException("todo!!");
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet, @Nullable ASTNode anchor) {
        throw new UnsupportedOperationException("todo!!");
    }

    @Override
    public PsiElement getPsi() {
        throw new UnsupportedOperationException("todo!!");
    }

    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        throw new UnsupportedOperationException("todo!!");
    }

    @Override
    public Object clone() {
        throw new UnsupportedOperationException("todo!!");
        // return super.clone();
    }
}
