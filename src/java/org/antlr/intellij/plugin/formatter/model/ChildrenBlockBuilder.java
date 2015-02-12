package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.Block;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2/11/15.
 */
public class ChildrenBlockBuilder extends AntlrBlockFactory {
    public final List<Block> blocks = new ArrayList<Block>();

    ChildrenBlockBuilder(@NotNull AntlrBlock parent) {
        super(parent);
    }

    public void addChild(ASTNode child) {
        if (include(child)) blocks.add(createBlock(child));
    }

    public void addChildrenOf(ASTNode parent) {
        for (ASTNode child = parent.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            addChild(child);
        }
    }

    protected boolean include(ASTNode node) {
        return !isWhitespaceOrEmpty(node);
    }

    private static boolean isWhitespaceOrEmpty(ASTNode node) {
        return node.getElementType() == TokenType.WHITE_SPACE || node.getTextLength() == 0;
    }
}
