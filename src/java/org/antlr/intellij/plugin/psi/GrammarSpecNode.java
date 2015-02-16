package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.plugin.tokens.AntlrRuleTypes;
import org.antlr.intellij.plugin.tokens.AntlrTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <pre>
 *     grammarSpec	:	DOC_COMMENT?
 * 						grammarType id SEMI
 * 						prequelConstruct*
 * 						rules
 * 						modeSpec*
 * 						EOF
 * ;
 * </pre>
 */
public class GrammarSpecNode extends AntlrPsiElement {

	public enum GrammarType {
		LEXER, PARSER, COMBINED, UNKNOWN;
	}

	/**
	 * <pre>
	 * grammarType:	(	LEXER GRAMMAR
	 * 				|	PARSER GRAMMAR
	 * 				|	GRAMMAR
	 * 				)
	 * ;
	 * </pre>
	 */
	static GrammarType grammarType(ASTNode grammarTypeNode) {
		if (grammarTypeNode == null) return GrammarType.UNKNOWN;
		ASTNode firstChild = grammarTypeNode.getFirstChildNode();
		if (firstChild == null) return GrammarType.UNKNOWN;
		IElementType type = firstChild.getElementType();
		if (AntlrTokenTypes.LEXER == type) return GrammarType.LEXER;
		if (AntlrTokenTypes.PARSER == type) return GrammarType.PARSER;
		if (AntlrTokenTypes.GRAMMAR == type) return GrammarType.COMBINED;
		return GrammarType.UNKNOWN;
	}
	@Nullable
	public PsiElement getIdElement() {
		return MyPsiUtils.getFirstChild(this, AntlrRuleTypes.id);
	}
	@Nullable
	public String getGrammarName() {
		PsiElement idElement = getIdElement();
		return idElement == null ? null : idElement.getText();
	}

	@Nullable
	public AntlrComment getHeaderComment() {
		PsiElement firstChild = getFirstChild();
		return firstChild instanceof AntlrComment ? (AntlrComment) firstChild : null;
	}

	@NotNull
	public GrammarType getGrammarType() {
		return grammarType(MyPsiUtils.getFirstChild(this.getNode(), AntlrRuleTypes.grammarType));
	}

//	public Iterable<RuleSpecNode> getRuleSpecs(){
//		RulesNode rulesNode =
//	}

	public GrammarSpecNode(@NotNull ASTNode node) {
		super(node);
	}

	@Override
	public void accept(@NotNull PsiElementVisitor visitor) {
		if(visitor instanceof AntlrPsiVisitor) ((AntlrPsiVisitor) visitor).visitGrammarSpec(this);
			else super.accept(visitor);
	}
}