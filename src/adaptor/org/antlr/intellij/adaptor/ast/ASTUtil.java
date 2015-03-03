package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.ForeignLeafPsiElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.ArrayFactory;
import com.intellij.util.CharTable;
import com.intellij.util.keyFMap.KeyFMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 3/1/15.
 */
public class ASTUtil {
    private static final Logger LOG = Logger.getInstance("#org.antlr.intellij.adaptor.ast.ASTUtil");


    public static CharTable findCharTable(ASTNode node) {
        while (node != null) {
            final CharTable userData = node.getUserData(CharTable.CHAR_TABLE_KEY);
            if (userData != null) return userData;
            if (node instanceof FileASTNode) return ((FileASTNode) node).getCharTable();
            node = node.getTreeParent();
        }
        LOG.error("Invalid root element");
        return null;
    }

    public static FileASTNode getFileNode(ASTNode node) {
        ASTNode root = getRootNode(node);
        return root instanceof FileASTNode ? (FileASTNode) root : null;
    }

    public static ASTNode getRootNode(ASTNode node) {
        ASTNode element = node;
        while (element.getTreeParent() != null) {
            element = element.getTreeParent();
        }
        return element;
    }

    public static PsiManagerEx getPsiManager(ASTNode node) {
        FileASTNode fileASTNode = getFileNode(node);
        if (fileASTNode == null) return null;
        PsiElement element = fileASTNode.getPsi();
        if (element == null) return null;
        return (PsiManagerEx) element.getManager();
    }

    public static ASTNode copyElement(ASTNode node) {
        CharTable table = findCharTable(node);
        ASTNode parent = node.getTreeParent();
        return null;
    }

    public static ASTNode copyElement(ASTNode original, PsiElement context, CharTable table) {
        ASTNode clone = (ASTNode) original.clone();
        PsiManager manager = getPsiManager(original);
        return null;
    }

    public static ASTNode[] collectChildren(ASTNode parent) {
        if (parent == null) return ASTNode.EMPTY_ARRAY;
        ASTNode firstChild = parent.getFirstChildNode();
        if (firstChild == null) return ASTNode.EMPTY_ARRAY;
        List<ASTNode> children = new ArrayList<ASTNode>();
        for (ASTNode child = firstChild; child != null; child = child.getTreeNext()) {
            children.add(child);
        }
        return children.toArray(new ASTNode[children.size()]);
    }

    @NotNull
    public static ASTNode[] collectChildren(@Nullable ASTNode parent, @NotNull TokenSet filter) {
        if (parent == null) return ASTNode.EMPTY_ARRAY;
        ASTNode firstChild = parent.getFirstChildNode();
        if (firstChild == null) return ASTNode.EMPTY_ARRAY;
        List<ASTNode> children = new ArrayList<ASTNode>();
        for (ASTNode child = firstChild; child != null; child = child.getTreeNext()) {
            if (filter.contains(child.getElementType())) {
                children.add(child);

            }
        }
        return children.toArray(new ASTNode[children.size()]);
    }

    @NotNull
    public static <N extends ASTNode> N[] collectChildren(@Nullable ASTNode parent, @NotNull TokenSet filter, ArrayFactory<N> factory) {
        if (parent == null) return factory.create(0);
        ASTNode firstChild = parent.getFirstChildNode();
        if (firstChild == null) return factory.create(0);
        List<N> children = new ArrayList<N>();
        for (ASTNode child = firstChild; child != null; child = child.getTreeNext()) {
            if (filter.contains(child.getElementType())) {
                children.add((N) child);

            }
        }
        return children.toArray(factory.create(children.size()));
    }

    public static <D> D getCopyableUserData(UserDataHolderBase dataHolder, Key<D> key) {
        return dataHolder.getCopyableUserData(key);
    }

    public static <T> void putCopyableUserData(UserDataHolderBase dataHolder, Key<T> key, T value) {
        dataHolder.putCopyableUserData(key, value);
    }

    public static <D> D getCopyableUserData(UserDataHolder dataHolder, Key<D> key) {
        KeyFMap map = dataHolder.getUserData(UserDataHolderBase.COPYABLE_USER_MAP_KEY);
        //noinspection unchecked,ConstantConditions
        return map == null ? null : map.get(key);
    }

    public static <T> void putCopyableUserData(UserDataHolder dataHolder, Key<T> key, T value) {
        KeyFMap copyableMap = dataHolder.getUserData(UserDataHolderBase.COPYABLE_USER_MAP_KEY);
        if (copyableMap == null) {
            copyableMap = KeyFMap.EMPTY_MAP;
        }
        KeyFMap newCopyableMap = value == null ? copyableMap.minus(key) : copyableMap.plus(key, value);
        dataHolder.putUserData(UserDataHolderBase.COPYABLE_USER_MAP_KEY, newCopyableMap);

    }

}
