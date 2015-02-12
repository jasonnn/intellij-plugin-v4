package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.plugin.ANTLRv4TokenTypes;
import org.antlr.intellij.plugin.parser.ANTLRv4Parser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        ChildrenBlockBuilder factory = new ChildrenBlockBuilder(this);
        for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            if (child.getElementType() == RULES) handleRules(factory, child);
            else factory.addChild(child);
        }
        return factory.blocks;
    }

    private void handleRules(ChildrenBlockBuilder factory, ASTNode rulesNode) {
        for (ASTNode ruleSpec = rulesNode.getFirstChildNode(); ruleSpec != null; ruleSpec = ruleSpec.getTreeNext()) {
            if (ruleSpec.getElementType() == RULESPEC) {
                for (ASTNode spec = ruleSpec.getFirstChildNode(); spec != null; spec = spec.getTreeNext()) {
                    factory.addChild(spec);
                }
            } else factory.addChild(ruleSpec);
        }
    }




    static final IElementType RULESPEC = ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_ruleSpec);
    static final IElementType RULES = ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_rules);
}
