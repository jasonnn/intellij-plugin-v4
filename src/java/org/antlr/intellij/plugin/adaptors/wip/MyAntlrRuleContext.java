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
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ErrorNodeImpl;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/23/15.
 */
public class MyAntlrRuleContext extends ParserRuleContext implements AntlrAST {
    @SuppressWarnings("MagicConstant")
    private final AntlrPsiAdapter astDelegate = new AntlrPsiAdapter(ANTLRv4TokenTypes.getRuleElementType(getRuleIndex()), this);

    public MyAntlrRuleContext() {
    }

    public MyAntlrRuleContext(ParserRuleContext parent, int invokingStateNumber) {
        super(parent, invokingStateNumber);
    }


    public TerminalNode addChild(Token matchedToken) {
        TerminalNodeImpl t = new MyTerminalNode(matchedToken);
        addChild(t);
        t.parent = this;
        return t;
    }

    public ErrorNode addErrorNode(Token badToken) {
        ErrorNodeImpl t = new MyErrorNode(badToken);
        addChild(t);
        t.parent = this;
        return t;
    }


    public TerminalNode addChild(TerminalNode t) {
        assert t instanceof ASTNode;
        return super.addChild(t);
    }

    public RuleContext addChild(RuleContext ruleInvocation) {
        assert ruleInvocation instanceof ASTNode;
        return super.addChild(ruleInvocation);
    }


    @Override
    public IElementType getElementType() {
        return astDelegate.getElementType();
    }

    @Override
    public String getText() {
        return astDelegate.getText();
    }

    @Override
    public CharSequence getChars() {
        return astDelegate.getChars();
    }

    @Override
    public boolean textContains(char c) {
        return astDelegate.textContains(c);
    }

    @Override
    public int getStartOffset() {
        return astDelegate.getStartOffset();
    }

    @Override
    public int getTextLength() {
        return astDelegate.getTextLength();
    }

    @Override
    public TextRange getTextRange() {
        return astDelegate.getTextRange();
    }

    @Override
    public ASTNode getTreeParent() {
        return astDelegate.getTreeParent();
    }

    @Override
    public ASTNode getFirstChildNode() {
        return astDelegate.getFirstChildNode();
    }

    @Override
    public ASTNode getLastChildNode() {
        return astDelegate.getLastChildNode();
    }

    @Override
    public ASTNode getTreeNext() {
        return astDelegate.getTreeNext();
    }

    @Override
    public ASTNode getTreePrev() {
        return astDelegate.getTreePrev();
    }

    @Override
    public ASTNode[] getChildren(TokenSet filter) {
        return astDelegate.getChildren(filter);
    }

    @Override
    public void addChild(@NotNull ASTNode child) {
        astDelegate.addChild(child);
    }

    @Override
    public void addChild(@NotNull ASTNode child, ASTNode anchorBefore) {
        astDelegate.addChild(child, anchorBefore);
    }

    @Override
    public void addLeaf(@NotNull IElementType leafType, CharSequence leafText, ASTNode anchorBefore) {
        astDelegate.addLeaf(leafType, leafText, anchorBefore);
    }

    @Override
    public void removeChild(@NotNull ASTNode child) {
        astDelegate.removeChild(child);
    }

    @Override
    public void removeRange(@NotNull ASTNode firstNodeToRemove, ASTNode firstNodeToKeep) {
        astDelegate.removeRange(firstNodeToRemove, firstNodeToKeep);
    }

    @Override
    public void replaceChild(@NotNull ASTNode oldChild, @NotNull ASTNode newChild) {
        astDelegate.replaceChild(oldChild, newChild);
    }

    @Override
    public void replaceAllChildrenToChildrenOf(ASTNode anotherParent) {
        astDelegate.replaceAllChildrenToChildrenOf(anotherParent);
    }

    @Override
    public void addChildren(ASTNode firstChild, ASTNode firstChildToNotAdd, ASTNode anchorBefore) {
        astDelegate.addChildren(firstChild, firstChildToNotAdd, anchorBefore);
    }

    @Override
    public ASTNode copyElement() {
        return astDelegate.copyElement();
    }

    @Nullable
    @Override
    public ASTNode findLeafElementAt(int offset) {
        return astDelegate.findLeafElementAt(offset);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type) {
        return astDelegate.findChildByType(type);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(IElementType type, @Nullable ASTNode anchor) {
        return astDelegate.findChildByType(type, anchor);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet) {
        return astDelegate.findChildByType(typesSet);
    }

    @Nullable
    @Override
    public ASTNode findChildByType(@NotNull TokenSet typesSet, @Nullable ASTNode anchor) {
        return astDelegate.findChildByType(typesSet, anchor);
    }

    @Override
    public PsiElement getPsi() {
        return astDelegate.getPsi();
    }

    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        return astDelegate.getPsi(clazz);
    }

    @Override
    public Object clone() {
        throw new UnsupportedOperationException("TODO!!!");
    }

    public String getUserDataString() {
        return astDelegate.getUserDataString();
    }

    public void copyUserDataTo(UserDataHolderBase other) {
        astDelegate.copyUserDataTo(other);
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return astDelegate.getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {
        astDelegate.putUserData(key, value);
    }

    @Nullable
    @Override
    public <T> T getCopyableUserData(Key<T> key) {
        return astDelegate.getCopyableUserData(key);
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, T value) {
        astDelegate.putCopyableUserData(key, value);
    }

    public <T> boolean replace(@NotNull Key<T> key, @Nullable T oldValue, @Nullable T newValue) {
        return astDelegate.replace(key, oldValue, newValue);
    }

    @NotNull
    public <T> T putUserDataIfAbsent(@NotNull Key<T> key, @NotNull T value) {
        return astDelegate.putUserDataIfAbsent(key, value);
    }

    public void copyCopyableDataTo(@NotNull UserDataHolderBase clone) {
        astDelegate.copyCopyableDataTo(clone);
    }

    public boolean isUserDataEmpty() {
        return astDelegate.isUserDataEmpty();
    }
}
