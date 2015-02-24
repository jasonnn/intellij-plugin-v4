package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolder;
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


    public MyAntlrRuleContext(ParserRuleContext parent, int invokingStateNumber) {
        super(parent, invokingStateNumber);
        //noinspection MagicConstant
        this.elementType = ANTLRv4TokenTypes.getRuleElementType(getRuleIndex());
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


    IElementType elementType;

    int siblingIndex = -1;
    private final UserDataHolder dataHolder = new UserDataHolderBase();

    @Override
    public PsiElement getPsi() {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
        throw new UnsupportedOperationException("todo");
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
    public int getSiblingIndex() {
        int index = siblingIndex;
        if (index == -1) {
            index = AntlrASTSupport.getSiblingIndex(this);
            assert index != -1;
            siblingIndex = index;
        }
        return index;
    }

//    @Override
//    public PsiElement getPsi() {
//        return null;
//    }
//
//    @Override
//    public <T extends PsiElement> T getPsi(@NotNull Class<T> clazz) {
//        return null;
//    }

    @Override
    public IElementType getElementType() {
        return elementType;
    }

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
        return AntlrASTSupport.handleClone(this);
    }
}
