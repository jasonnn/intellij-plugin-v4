package org.antlr.intellij.plugin.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by jason on 2/10/15.
 *
 * @see com.intellij.json.formatter.JsonBlock
 */
public class AntlrBlock extends AbstractBlock {
    private final AntlrBlock parent;
    private final CodeStyleSettings mySettings;
    private final Indent indent;

    protected AntlrBlock(@NotNull ASTNode node,
                         @Nullable Wrap wrap,
                         @Nullable Alignment alignment,
                         @NotNull Indent indent,
                         @Nullable AntlrBlock parent,
                         @NotNull CodeStyleSettings settings) {
        super(node, wrap, alignment);
        this.indent=indent;
        this.parent=parent;
        this.mySettings=settings;

    }

    @Override
    protected List<Block> buildChildren() {
        return null;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
