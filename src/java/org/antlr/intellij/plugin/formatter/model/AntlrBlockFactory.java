package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.RuleElementType;
import org.antlr.intellij.plugin.ANTLRv4TokenTypes;
import org.antlr.intellij.plugin.parser.ANTLRv4Parser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/11/15.
 */
public class AntlrBlockFactory {
    final AntlrBlock parent;
    final CodeStyleSettings settings;

    AntlrBlockFactory(@NotNull AntlrBlock parent) {
        this(parent, parent.mySettings);
    }

    AntlrBlockFactory(@Nullable AntlrBlock parent, @NotNull CodeStyleSettings settings) {
        this.parent = parent;
        this.settings = settings;
    }

    public AntlrBlock createBlock(@NotNull ASTNode node) {
        return createBlock(node, Indent.getNoneIndent());
    }

    public AntlrBlock createBlock(@NotNull ASTNode node,
                                  @NotNull Indent indent) {
        return createBlock(node, null, null, indent);
    }

    public AntlrBlock createBlock(@NotNull ASTNode node,
                                  @Nullable Wrap wrap,
                                  @Nullable Alignment alignment,
                                  @NotNull Indent indent) {
        return createBlock(node, wrap, alignment, indent, parent, settings);
    }

    public static AntlrBlock createBlock(@NotNull ASTNode node,
                                         @Nullable Wrap wrap,
                                         @Nullable Alignment alignment,
                                         @NotNull Indent indent,
                                         @Nullable AntlrBlock parent,
                                         @NotNull CodeStyleSettings settings) {
        IElementType type = node.getElementType();
        if (type == GRAMMAR_SPEC) {
            return new GrammarBlock(node, wrap, alignment, indent, parent, settings);
        }
        if (type == RULESPEC) {
            return new RuleBlock(node, wrap, alignment, indent, parent, settings);
        }

        return new AntlrBlock(node, wrap, alignment, indent, parent, settings);
    }

    static final IElementType GRAMMAR_SPEC = ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_grammarSpec);
    private static final RuleElementType RULESPEC = ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_ruleSpec);

}
