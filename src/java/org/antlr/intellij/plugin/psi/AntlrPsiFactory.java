package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.ast.AntlrWhitespace;
import org.antlr.intellij.adaptor.lexer.ElementTypeFactory;
import org.antlr.intellij.adaptor.psi.AntlrPsiElement;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.antlr.intellij.plugin.parser.ANTLRv4Parser;

import static org.antlr.intellij.plugin.ANTLRv4TokenTypes.getRuleElementType;

/**
 * Created by jason on 2/24/15.
 */
public class AntlrPsiFactory {

    public static PsiElement createElement(ASTNode node) {
        final IElementType elementType = node.getElementType();

        //TODO remove EOF from parse tree?
        if (elementType == ElementTypeFactory.getEofElementType(ANTLRv4Language.INSTANCE)) {
            return new AntlrWhitespace(node);
        }

        if (elementType == getRuleElementType(ANTLRv4Parser.RULE_rules)) {
            return new RulesNode(node);
        }
        if (elementType == getRuleElementType(ANTLRv4Parser.RULE_parserRuleSpec)) {
            return new ParserRuleSpecNode(node);
        }
        if (elementType == getRuleElementType(ANTLRv4Parser.RULE_lexerRule)) {
            return new LexerRuleSpecNode(node);
        }
        if (elementType == getRuleElementType(ANTLRv4Parser.RULE_grammarSpec)) {
            return new GrammarSpecNode(node);
        }
        if (elementType == getRuleElementType(ANTLRv4Parser.RULE_action)) {
            return new AtAction(node);
        }


        return new AntlrPsiElement(node);
    }
}
