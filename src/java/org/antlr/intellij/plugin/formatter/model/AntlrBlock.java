package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.antlr.intellij.plugin.formatter.AntlrCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
        List<Block> children = new ArrayList<Block>();
        for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            if (includeChild(child)) {
                children.add(makeSubBlock(child));
            }
        }
        return children;
    }

    protected boolean includeChild(ASTNode child) {
        return !isWhitespaceOrEmpty(child);
    }

    protected Block makeSubBlock(@NotNull ASTNode childNode) {
        Indent indent = Indent.getNoneIndent();
        Alignment alignment = Alignment.createAlignment();
        Wrap wrap = Wrap.createWrap(WrapType.NONE, false);

        return AntlrBlockFactory.createBlock(childNode, wrap, alignment, indent, this, mySettings);
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

    private static boolean isWhitespaceOrEmpty(ASTNode node) {
        return node.getElementType() == TokenType.WHITE_SPACE || node.getTextLength() == 0;
    }

    private AntlrCodeStyleSettings getCustomSettings() {
        return mySettings.getCustomSettings(AntlrCodeStyleSettings.class);
    }

    private CommonCodeStyleSettings getCommonSettings() {
        return mySettings.getCommonSettings(ANTLRv4Language.INSTANCE);
    }
}
