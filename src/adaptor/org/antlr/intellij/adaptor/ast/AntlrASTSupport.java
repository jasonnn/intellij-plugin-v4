package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

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
        //TODO probably not correct;
        return tree.getSourceInterval().a;
    }

    public static int getTextLength(ParseTree tree) {
        return tree.getText().length();
    }

    public static TextRange getTextRange(ParseTree tree) {
        Interval interval = tree.getSourceInterval();
        return TextRange.from(interval.a, interval.length());
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
        int nextIndex = tree.getSiblingIndex() - 1;
        if (nextIndex <= 0) return null;
        return (ASTNode) parent.getChild(nextIndex);
    }

    public static ASTNode[] getChildren(ParseTree tree, TokenSet filter) {
        //TODO
        return new ASTNode[0];
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
        throw new UnsupportedOperationException("todo!!");
    }

    public static <D> D getCopyableUserData(UserDataHolder dataHolder, Key<D> key) {
        throw new UnsupportedOperationException("todo!!");
    }

    public static <T> void putCopyableUserData(UserDataHolder dataHolder, Key<T> key, T value) {
        throw new UnsupportedOperationException("todo!!");
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
}
