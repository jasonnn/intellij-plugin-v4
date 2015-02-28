package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.antlr.intellij.adaptor.parser.PsiElementFactory;
import org.antlr.intellij.adaptor.psi.AntlrPsiElement;
import org.jetbrains.annotations.NotNull;

public class RulesNode extends AntlrPsiElement {
	public RulesNode(@NotNull ASTNode node) {
		super(node);
	}

	public static class Factory implements PsiElementFactory {
		public static Factory INSTANCE = new Factory();

		@Override
		public PsiElement createElement(ASTNode node) {
			return new RulesNode(node);
		}
	}
}
