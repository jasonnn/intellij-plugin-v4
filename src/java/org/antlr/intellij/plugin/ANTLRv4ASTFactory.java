package org.antlr.intellij.plugin;

import com.intellij.lang.ASTFactory;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.antlr.intellij.plugin.parser.ANTLRv4Lexer;
import org.antlr.intellij.plugin.psi.LexerRuleRefNode;
import org.antlr.intellij.plugin.psi.ParserRuleRefNode;
import org.jetbrains.annotations.NotNull;

public class ANTLRv4ASTFactory extends ASTFactory {

	/** Create a FileElement for root or a parse tree CompositeElement (not
	 *  PSI) for the token. This impl is more or less the default.
	 */
    @Override
    public CompositeElement createComposite(IElementType type) {
        if (type instanceof IFileElementType) {
            return new FileElement(type, null);
		}
        return new CompositeElement(type);
    }

	/** Create PSI nodes out of tokens so even parse tree sees them as such.
	 *  Does not see whitespace tokens.
	 */
    @Override
    public LeafElement createLeaf(@NotNull IElementType type, CharSequence text) {
		LeafElement t;
		if ( type == ANTLRv4TokenTypes.TOKEN_ELEMENT_TYPES.get(ANTLRv4Lexer.RULE_REF) ) {
			t = new ParserRuleRefNode(type, text);
		}
		else if ( type == ANTLRv4TokenTypes.TOKEN_ELEMENT_TYPES.get(ANTLRv4Lexer.TOKEN_REF) ) {
			t = new LexerRuleRefNode(type, text);
		}
		else {
			t = new LeafPsiElement(type, text);
		}
//		System.out.println("createLeaf "+t+" from "+type+" "+text);
		return t;
    }

}
