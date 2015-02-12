package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.plugin.ANTLRv4TokenTypes;
import org.antlr.intellij.plugin.parser.ANTLRv4Parser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2/11/15.
 */
public class GrammarBlock extends AntlrBlock {
    protected GrammarBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, @NotNull Indent indent, @Nullable AntlrBlock parent, @NotNull CodeStyleSettings settings) {
        super(node, wrap, alignment, indent, parent, settings);
    }

    @Override
    protected List<Block> buildChildren() {
        AntlrBlockFactory factory = new AntlrBlockFactory(this);
        List<Block> children = new ArrayList<Block>();
        for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            IElementType type = child.getElementType();
            if (type == RULES) handleRules(children, factory, child);
            else if (includeChild(child)) children.add(factory.createBlock(child));
        }
        return children;
    }

    private void handleRules(List<Block> blocks, AntlrBlockFactory factory, ASTNode rulesNode) {
        for (ASTNode ruleSpec = rulesNode.getFirstChildNode(); ruleSpec != null; ruleSpec = ruleSpec.getTreeNext()) {
            //could also be whitespace
            if (ruleSpec.getElementType() == RULESPEC) {
                for (ASTNode spec = ruleSpec.getFirstChildNode(); spec != null; spec = spec.getTreeNext()) {
                    if (RULESPECS.contains(spec.getElementType())) {
                        blocks.add(factory.createBlock(spec));
                    }
                    else if (includeChild(spec)) blocks.add(factory.createBlock(spec));
                }
            }
            else if (includeChild(ruleSpec)) blocks.add(factory.createBlock(ruleSpec));

        }
    }

    private static final TokenSet RULESPECS = TokenSet.create(
            ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_parserRuleSpec),
            ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_lexerRule)
    );

    private static final TokenSet RULE_BLOCKS = TokenSet.create(
            ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_lexerBlock),
            ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_ruleBlock)
    );


    static final IElementType RULESPEC = ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_ruleSpec);
    static final IElementType RULES = ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_rules);
}
