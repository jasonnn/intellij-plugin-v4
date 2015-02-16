package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.antlr.intellij.plugin.ANTLRv4TokenTypes;
import org.antlr.intellij.plugin.tokens.AntlrRuleTypes;

import java.util.HashMap;

/**
 * Created by jason on 2/12/15.
 */
public class AntlrPsiElementFactory {

    public static PsiElement createElement(ASTNode node) {
        PsiElementFactory factory = factories.get(node.getElementType());
        return factory != null ? factory.createElement(node) : doCreateElement(node);


    }

     static PsiElement doCreateElement(ASTNode node) {
         final IElementType type = node.getElementType();

         if(ANTLRv4TokenTypes.COMMENTS.contains(type)){
             return new AntlrPsiElement(node);
         }

		 return new AntlrPsiElement(node);

    }


    static final HashMap<IElementType, PsiElementFactory> factories = new HashMap<IElementType, PsiElementFactory>();

    static {
        for (EnumBasedPsiFactory factory : EnumBasedPsiFactory.values()) {
            factories.put(factory.type, factory);
        }
    }
    //probably faster than having lots of ifs, but also more verbose
    enum EnumBasedPsiFactory implements PsiElementFactory {

        Rules(AntlrRuleTypes.rules) {
            @Override
            public PsiElement doCreateElement(ASTNode node) {
                return new RulesNode(node);
            }
        },
        ParserRuleSpec(AntlrRuleTypes.parserRuleSpec) {
            @Override
            public PsiElement doCreateElement(ASTNode node) {
                return new ParserRuleSpecNode(node);
            }
        },
        LexerRuleSpec(AntlrRuleTypes.lexerRule) {
            @Override
            public PsiElement doCreateElement(ASTNode node) {
                return new LexerRuleSpecNode(node);
            }
        },
        GrammarSpec(AntlrRuleTypes.grammarSpec) {
            @Override
            public PsiElement doCreateElement(ASTNode node) {
                return new GrammarSpecNode(node);
            }
        },
        AtAction(AntlrRuleTypes.action) {
            @Override
            public PsiElement doCreateElement(ASTNode node) {
                return new AtAction(node);
            }
        };

        public final PsiElement createElement(ASTNode node) {
            assert this.type == node.getElementType();
            return doCreateElement(node);
        }

        abstract PsiElement doCreateElement(ASTNode node);

        final IElementType type;

        EnumBasedPsiFactory(IElementType type) {
            this.type = type;
        }
    }


}
