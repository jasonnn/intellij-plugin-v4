package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiWhiteSpace;
import org.antlr.intellij.adaptor.lexer.ElementTypeFactory;
import org.antlr.intellij.adaptor.psi.AntlrPsiElement;
import org.antlr.intellij.plugin.ANTLRv4Language;

/**
 * Created by jason on 2/26/15.
 */
public class AntlrWhitespace extends AntlrPsiElement implements PsiWhiteSpace {
    public AntlrWhitespace(ASTNode node) {
        super(node);
    }

    @Override
    public String getText() {
        return getNode().getElementType() == ElementTypeFactory.getEofElementType(ANTLRv4Language.INSTANCE) ? "EOF" : super.getText();
    }
}
