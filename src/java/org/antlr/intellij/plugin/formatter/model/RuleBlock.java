package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/11/15.
 * <pre>
 * lexerRule:	DOC_COMMENT? FRAGMENT?
 *              TOKEN_REF COLON lexerRuleBlock SEMI
 *          ;
 * </pre>
 * <p/>
 * <pre>
 *     parserRuleSpec:	DOC_COMMENT?
 *                      ruleModifiers? RULE_REF ARG_ACTION?
 *                      ruleReturns? throwsSpec? localsSpec?
 *                      rulePrequel*
 *                      COLON
 *                      ruleBlock
 *                      SEMI
 *                      exceptionGroup
 *                      ;
 * </pre>
 * <p/>
 * ;
 */
public class RuleBlock extends AntlrBlock {
    protected RuleBlock(@NotNull ASTNode node, @Nullable AntlrBlock parent, @NotNull CodeStyleSettings settings) {
        super(node, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(), Indent.getAbsoluteNoneIndent(), parent, settings);
      //  System.out.println("RuleBlock.RuleBlock");
    }

    @Override
    protected void doBuildChildren(ChildrenBlockBuilder builder) {
        super.doBuildChildren(builder);
    }
}
