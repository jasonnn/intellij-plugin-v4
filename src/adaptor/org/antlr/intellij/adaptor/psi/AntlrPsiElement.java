package org.antlr.intellij.adaptor.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.antlr.intellij.adaptor.ast.AntlrAST;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by jason on 2/24/15.
 */
public class AntlrPsiElement extends ASTWrapperPsiElement {
    public AntlrPsiElement(ASTNode node) {
        super(node);
        assert node instanceof AntlrAST;
    }

    public ParseTree getParseTree() {
        return (ParseTree) getNode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getParseTree().getClass().getSimpleName() + "](" + getNode().getElementType().toString() + ")";
    }
}
