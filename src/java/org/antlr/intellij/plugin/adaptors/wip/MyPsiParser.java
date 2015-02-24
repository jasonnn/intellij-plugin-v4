package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.antlr.intellij.adaptor.lexer.PsiTokenSource;
import org.antlr.intellij.adaptor.parser.SyntaxErrorListener;
import org.antlr.intellij.plugin.ANTLRv4TokenTypes;
import org.antlr.intellij.plugin.parser.ANTLRv4Lexer;
import org.antlr.intellij.plugin.parser.ANTLRv4Parser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jason on 2/23/15.
 */
public class MyPsiParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        TokenStream tokenStream = new CommonTokenStream(new PsiTokenSource(builder));
        ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());

        int startRule;
        if (root instanceof IFileElementType) {
            startRule = ANTLRv4Parser.RULE_grammarSpec;
        } else if (root == ANTLRv4TokenTypes.TOKEN_ELEMENT_TYPES.get(ANTLRv4Lexer.TOKEN_REF)
                || root == ANTLRv4TokenTypes.TOKEN_ELEMENT_TYPES.get(ANTLRv4Lexer.RULE_REF)) {
            startRule = ANTLRv4Parser.RULE_atom;
        } else {
            startRule = Token.INVALID_TYPE;
        }

        switch (startRule) {
            case ANTLRv4Parser.RULE_grammarSpec:
                return parser.grammarSpec();

            case ANTLRv4Parser.RULE_atom:
                return parser.atom();

            default:
                String ruleName = ANTLRv4Parser.ruleNames[startRule];
                throw new UnsupportedOperationException(String.format("cannot start parsing using root element %s", root));
        }
    }
}
