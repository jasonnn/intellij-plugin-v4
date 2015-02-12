package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.antlr.intellij.plugin.formatter.AntlrCodeStyleSettings;
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
    final CodeStyleSettings mySettings;
    private final Indent indent;
    private final SpacingBuilder mySpacingBuilder;


    protected AntlrBlock(@NotNull ASTNode node,
                         @Nullable Wrap wrap,
                         @Nullable Alignment alignment,
                         @NotNull Indent indent,
                         @Nullable AntlrBlock parent,
                         @NotNull CodeStyleSettings settings) {
        super(node, wrap, alignment);
        this.indent = indent;
        this.parent = parent;
        this.mySettings = settings;
        mySpacingBuilder = ANTLRv4FormattingModelBuilder.createSpacingBuilder(settings);

    }

    @Override
    protected List<Block> buildChildren() {
        ChildrenBlockBuilder childrenFactory = new ChildrenBlockBuilder(this);
        doBuildChildren(childrenFactory);
        return childrenFactory.blocks;
    }

    protected void doBuildChildren(ChildrenBlockBuilder builder) {
        for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            builder.addChild(child);
        }
    }


    @Nullable
    @Override
    public Spacing getSpacing(Block child1, @NotNull Block child2) {
        return mySpacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }


    protected AntlrCodeStyleSettings getCustomSettings() {
        return mySettings.getCustomSettings(AntlrCodeStyleSettings.class);
    }

    protected CommonCodeStyleSettings getCommonSettings() {
        return mySettings.getCommonSettings(ANTLRv4Language.INSTANCE);
    }
}
