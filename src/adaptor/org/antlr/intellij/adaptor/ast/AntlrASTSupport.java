package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.util.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.ForeignLeafPsiElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Created by jason on 2/24/15.
 */
public class AntlrASTSupport {
    //TODO: see if theres any overlap with com.intellij.psi.impl.source.tree.SharedImplUtil

    public static CharSequence getChars(ParseTree tree) {
        return tree.getText();
    }

    public static boolean textContains(ParseTree tree, char c) {
        CharSequence chars = tree.getText();
        for (int i = 0; i < chars.length(); i++) {
            if (chars.charAt(i) == c) return true;
        }
        return false;
    }

    public static int getStartOffset(ParseTree tree) {
        Object payload = tree.getPayload();
        if (payload instanceof ParserRuleContext) {
            return startOfs(((ParserRuleContext) payload).start);
        }
        if (payload instanceof Token) {
            return startOfs((Token) payload);
        }
        throw new UnsupportedOperationException("???");
        // return tree.getSourceInterval().a;
    }

    public static int getStopOffset(ParseTree tree) {
        Object payload = tree.getPayload();
        if (payload instanceof ParserRuleContext) {
            return startOfs(((ParserRuleContext) payload).stop);
        }
        if (payload instanceof Token) {
            return endOfs((Token) payload);
        }
        throw new UnsupportedOperationException("???");
    }

    static int startOfs(Token t) {
        int startIndex = t.getStartIndex();
        assert startIndex != -1;
        return startIndex;
    }

    static int endOfs(Token t) {
        int endIndex = t.getStopIndex();
        assert endIndex != -1;
        return endIndex;
    }

    public static int getTextLength(ParseTree tree) {
        String text = tree.getText();
        if (text == null) return 0;
        return text.length();
    }

    public static TextRange getTextRange(ParseTree tree) {
        int start = getStartOffset(tree);
        int stop = getStopOffset(tree);
        if (start > stop) {
            Object payload = tree.getPayload();
            if (payload instanceof TerminalNode) {
                payload = ((TerminalNode) payload).getSymbol();
            }
            if (!(payload instanceof Token)) {
                return TextRange.create(getStopOffset(tree), getStartOffset(tree));
            }
            //assert payload instanceof Token: payload.getClass();
            Token t = (Token) payload;
            assert t.getType() == Token.EOF;
            return TextRange.from(getStartOffset(tree), 0);
            // return new UnfairTextRange(getStartOffset(tree),getStopOffset(tree));
        }
        return TextRange.create(getStartOffset(tree), getStopOffset(tree));
        // Interval interval = tree.getSourceInterval();
        // return TextRange.from(interval.a, interval.length());
    }

    public static ASTNode getTreeParent(ParseTree tree) {
        return (ASTNode) tree.getParent();
    }

    public static ASTNode getFirstChildNode(SiblingIndexParseTree tree) {
        ParseTree child = tree.getChildCount() > 0 ? tree.getChild(0) : null;
        return (ASTNode) child;
    }

    public static ASTNode getLastChildNode(SiblingIndexParseTree tree) {
        int childCount = tree.getChildCount();
        ParseTree child = childCount > 0 ? tree.getChild(childCount - 1) : null;
        return (ASTNode) child;
    }

    public static ASTNode getTreeNext(SiblingIndexParseTree tree) {
        ParseTree parent = tree.getParent();
        if (parent == null) return null;
        int nextIndex = tree.getSiblingIndex() + 1;
        if (parent.getChildCount() <= nextIndex) return null;
        return (ASTNode) parent.getChild(nextIndex);
    }

    public static ASTNode getTreePrev(SiblingIndexParseTree tree) {
        ParseTree parent = tree.getParent();
        if (parent == null) return null;
        int prevIndex = tree.getSiblingIndex() - 1;
        if (prevIndex < 0) return null;
        return (ASTNode) parent.getChild(prevIndex);
    }

    public static ASTNode[] getChildren(ASTNode parent, TokenSet filter) {
        return filter == null ? ASTUtil.collectChildren(parent) : ASTUtil.collectChildren(parent, filter);
    }

    public static void addChild(ParseTree tree, ASTNode child) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static void addChild(ParseTree tree, ASTNode child, ASTNode anchorBefore) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static void addLeaf(ParseTree tree, IElementType leafType, CharSequence leafText, ASTNode anchorBefore) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static void removeChild(ParseTree tree, ASTNode child) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static void removeRange(ParseTree tree, ASTNode firstNodeToRemove, ASTNode firstNodeToKeep) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static void replaceChild(ParseTree tree, ASTNode oldChild, ASTNode newChild) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static void replaceAllChildrenToChildrenOf(ParseTree tree, ASTNode anotherParent) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static void addChildren(ParseTree tree, ASTNode firstChild, ASTNode firstChildToNotAdd, ASTNode anchorBefore) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static ASTNode copyElement(ParseTree tree) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static ASTNode findLeafElementAt(ParseTree tree, int offset) {
        if (tree instanceof TerminalNode) {
            return (ASTNode) tree;
        }
        ASTNode element = (ASTNode) tree;
        startFind:
        while (true) {
            ASTNode child = element.getFirstChildNode();
            ASTNode lastChild = element.getLastChildNode();
            int elementTextLength = element.getTextLength();
            boolean fwd = lastChild == null || elementTextLength / 2 > offset;
            if (!fwd) {
                child = lastChild;
                offset = elementTextLength - offset;
            }
            while (child != null) {
                final int textLength = child.getTextLength();
                if (textLength > offset || !fwd && textLength >= offset) {
                    if (child instanceof TerminalNode) {
//                        if (child instanceof ForeignLeafPsiElement) {
//                            child = fwd ? child.getTreeNext() : child.getTreePrev();
//                            continue;
//                        }
                        return child;
                    }
                    offset = fwd ? offset : textLength - offset;
                    element = child;
                    continue startFind;
                }
                offset -= textLength;
                child = fwd ? child.getTreeNext() : child.getTreePrev();
            }
            return null;
        }
    }

    public static <D> D getCopyableUserData(UserDataHolderBase dataHolder, Key<D> key) {
        return ASTUtil.getCopyableUserData(dataHolder, key);
    }

    public static <T> void putCopyableUserData(UserDataHolderBase dataHolder, Key<T> key, T value) {
        ASTUtil.putCopyableUserData(dataHolder, key, value);
    }

    public static <D> D getCopyableUserData(UserDataHolder dataHolder, Key<D> key) {
        return ASTUtil.getCopyableUserData(dataHolder, key);
    }

    public static <T> void putCopyableUserData(UserDataHolder dataHolder, Key<T> key, T value) {
        ASTUtil.putCopyableUserData(dataHolder, key, value);
    }

    public static ASTNode findChildByType(ParseTree tree, IElementType type) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static ASTNode findChildByType(ParseTree tree, IElementType type, ASTNode anchor) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static ASTNode findChildByType(ParseTree tree, TokenSet typesSet) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static ASTNode findChildByType(ParseTree tree, TokenSet typesSet, ASTNode anchor) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static Object handleClone(ParseTree tree) {
        throw new UnsupportedOperationException("todo!!");
    }

    private static Key<Integer> kSiblingIndex = Key.create("Antlr.siblingIndex");

    public static int getSiblingIndexCached(ParseTree tree) {
        UserDataHolder dataHolder = (UserDataHolder) tree;
        Integer value = dataHolder.getUserData(kSiblingIndex);
        if (value == null) {
            value = getSiblingIndex(tree);
            dataHolder.putUserData(kSiblingIndex, value);
        }
        return value;
    }

    public static int getSiblingIndex(ParseTree tree) {
        int index = -1;
        ParseTree parent = tree.getParent();
        if (parent == null) {
            index = 0;
        } else {
            for (int i = 0; i < parent.getChildCount(); i++) {
                ParseTree child = parent.getChild(i);
                if (child == tree) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    private static final Key<PsiElement> kPsi = Key.create("Antlr.psi");

    public static PsiElement getPsiCached(ASTNode node) {
        PsiElement element = node.getUserData(kPsi);
        if (element == null) {
            element = getPsi(node);
            node.putUserData(kPsi, element);
        }
        return element;
    }

    public static PsiElement getPsi(ASTNode node) {
        final Language lang = node.getElementType().getLanguage();
        final ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang);
        if (parserDefinition != null) {
            return parserDefinition.createElement(node);
        }
        //noinspection ConstantConditions
        return null;
    }

    public static <T extends PsiElement> T getPsiCached(ASTNode node, Class<T> clazz) {
        return clazz.cast(getPsiCached(node));
    }

    public static <T extends PsiElement> T getPsi(ASTNode node, Class<T> clazz) {
        return clazz.cast(getPsi(node));
    }

    public static void copyNodeAndReparentChildren(ParserRuleContext original, ParserRuleContext newCtx) {
        newCtx.copyFrom(original);
        newCtx.children = original.children;
        reparentChildren(newCtx);
    }

    static void reparentChildren(ParserRuleContext parent) {
        reparentChildren(parent.children, parent);
    }

    public static void reparentChildren(@Nullable List<ParseTree> children, @NotNull RuleContext newParent) {
        if (children == null) children = Collections.emptyList();
        doReparentChildren(children, newParent);
    }

    static void doReparentChildren(List<ParseTree> children, RuleContext newParent) {
        for (ParseTree child : children) {
            if (child instanceof RuleContext) {
                ((RuleContext) child).parent = newParent;
            } else if (child instanceof TerminalNodeImpl) {
                ((TerminalNodeImpl) child).parent = newParent;
            } else {
                throw new UnsupportedOperationException("???");
            }
        }
    }

}
