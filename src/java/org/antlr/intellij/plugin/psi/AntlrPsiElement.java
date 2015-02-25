package org.antlr.intellij.plugin.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.antlr.intellij.plugin.adaptors.wip.AntlrAST;

/**
 * Created by jason on 2/24/15.
 */
public class AntlrPsiElement extends ASTWrapperPsiElement {
    public AntlrPsiElement(ASTNode node) {
        super(node);
        assert node instanceof AntlrAST;
    }
}