package org.antlr.intellij.plugin.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

/**
 * Created by jason on 2/13/15.
 */
public class AntlrPsiElement extends ASTWrapperPsiElement{
	public AntlrPsiElement(ASTNode node) {
		super(node);
	}
}
