package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <pre>
 * lexerRuleBlock:lexerAltList;
 *
 * lexerAltList:lexerAlt (OR lexerAlt)*;
 *
 * lexerAlt:lexerElements lexerCommands?
 *         |
 *         ;
 *
 * lexerElements:lexerElement+;
 *
 * lexerElement
 * :	labeledLexerElement ebnfSuffix?
 * |	lexerAtom ebnfSuffix?
 * |	lexerBlock ebnfSuffix?
 * |	ACTION QUESTION? // actions only allowed at end of outer alt actually,
 * // but preds can be anywhere
 * ;
 *
 * </pre>
 * Created by jason on 2/11/15.
 */
public class RuleBlockBlock extends AntlrBlock {
    protected RuleBlockBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, @NotNull Indent indent, @Nullable AntlrBlock parent, @NotNull CodeStyleSettings settings) {
        super(node, wrap, alignment, indent, parent, settings);
    }

    @Override
    protected void doBuildChildren(ChildrenBlockBuilder builder) {
        super.doBuildChildren(builder);
    }
}
