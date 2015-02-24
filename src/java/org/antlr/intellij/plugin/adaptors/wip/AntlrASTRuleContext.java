package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.plugin.ANTLRv4TokenTypes;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by jason on 2/15/15.
 */
public class AntlrASTRuleContext extends ParserRuleContext implements ASTNode {


    private final IElementType myType;


    private UserDataHolderBase dataHolderDelegate;


    public AntlrASTRuleContext(IElementType myType) {
        this.myType = myType;
    }

    public AntlrASTRuleContext(ParserRuleContext parent, int invokingState) {
        super(parent, invokingState);
        myType = ANTLRv4TokenTypes.getRuleElementType(getRuleIndex());
    }

    /*--------------UserDataHolder methods--------------*/
    protected UserDataHolderBase dataHolder() {
        UserDataHolderBase dataHolderBase = dataHolderDelegate;
        if (dataHolderBase == null) {
            dataHolderBase = dataHolderDelegate = new UserDataHolderBase();
        }
        return dataHolderBase;
    }

    public String getUserDataString() {
        return dataHolder().getUserDataString();
    }

    public void copyUserDataTo(UserDataHolderBase other) {
        dataHolder().copyUserDataTo(other);
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return dataHolder().getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {
        dataHolder().putUserData(key, value);
    }

    @Nullable
    @Override
    public <T> T getCopyableUserData(Key<T> key) {
        return dataHolder().getCopyableUserData(key);
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, T value) {
        dataHolder().putCopyableUserData(key, value);
    }

    public <T> boolean replace(@NotNull Key<T> key, @Nullable T oldValue, @Nullable T newValue) {
        return dataHolder().replace(key, oldValue, newValue);
    }

    @NotNull
    public <T> T putUserDataIfAbsent(@NotNull Key<T> key, @NotNull T value) {
        return dataHolder().putUserDataIfAbsent(key, value);
    }

    public void copyCopyableDataTo(@NotNull UserDataHolderBase clone) {
        dataHolder().copyCopyableDataTo(clone);
    }

    public boolean isUserDataEmpty() {
        return dataHolder().isUserDataEmpty();
    }

    /*--------------ASTNode methods-----------------*/
    @Override
    public IElementType getElementType() {
        return myType;
    }

    @Override
    public CharSequence getChars() {
        return super.getText();
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
        return super.getStart().getStartIndex();
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
        return (ASTNode) super.parent;
    }

    @Override
    public ASTNode getFirstChildNode() {
        if (children == null) return null;
        return (ASTNode) children.get(0);
    }

    @Override
    public ASTNode getLastChildNode() {
        List<ParseTree> c = children;
        if (children == null) return null;
        return (ASTNode) c.get(c.size() - 1);

    }

    protected int getSiblingIndex() {
        ParserRuleContext p = (ParserRuleContext) parent;
        if (p == null) return -1;
        else return p.children.indexOf(this);
    }

    @Override
    public ASTNode getTreeNext() {
        ParserRuleContext p = (ParserRuleContext) parent;
        if (p == null) return null;
        int index = getSiblingIndex() + 1;

        return (index >= p.getChildCount()) ? null : (ASTNode) p.getChild(index);
    }

    @Override
    public ASTNode getTreePrev() {
        ParserRuleContext p = (ParserRuleContext) parent;
        if (p == null) return null;
        int index = getSiblingIndex() + 1;
        return index <= 0 ? null : (ASTNode) p.getChild(index);
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public ASTNode[] getChildren(@Nullable TokenSet filter) {
        return children.toArray(new ASTNode[children.size()]);

    }

    @Override
    public void addChild(@NotNull ASTNode child) {
        if (child instanceof RuleContext) super.addChild((RuleContext) child);
        else if (child instanceof Token) super.addChild((Token) child);
        else if (child instanceof TerminalNode) super.addChild((TerminalNode) child);
        else throw new IllegalArgumentException(child + " not an antlr object!");

    }

    @Override
    public void addChild(@NotNull ASTNode child, @Nullable ASTNode anchorBefore) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLeaf(@NotNull IElementType leafType, CharSequence leafText, @Nullable ASTNode anchorBefore) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeChild(@NotNull ASTNode child) {
        children.remove(child);
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
        AntlrASTRuleContext newNode = new AntlrASTRuleContext(this.myType);
        newNode.copyFrom(this);
        //TODO userdata
        return newNode;
    }

    @Nullable
    @Override
    public ASTNode findLeafElementAt(int offset) {
        throw new UnsupportedOperationException("todo!");
    }



    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type) {
        throw new UnsupportedOperationException("todo!");

    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type, @Nullable ASTNode anchor) {
        throw new UnsupportedOperationException("todo!");

    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet) {
        throw new UnsupportedOperationException("todo!");

    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet, @Nullable ASTNode anchor) {
        throw new UnsupportedOperationException("todo!");

    }

    @Override
    public PsiElement getPsi() {
        return null;
    }

    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        return null;
    }


    @Override
    public Object clone() {
        try {
            //TODO
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
