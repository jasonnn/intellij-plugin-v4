package org.antlr.intellij.adaptor.elementtype;

import com.intellij.lang.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IFileElementType;
import org.antlr.intellij.adaptor.ast.AntlrFileNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/25/15.
 *
 * this is where parsing actually starts.
 */
public class AntlrFileElementType extends IFileElementType {

    public AntlrFileElementType(@NotNull String debugName, @Nullable Language language) {
        super(debugName, language);
    }

    public AntlrFileElementType(@Nullable Language language) {
        super(language);
    }



    @Nullable
    @Override
    public ASTNode parseContents(ASTNode chameleon) {
        final PsiElement psi = chameleon.getPsi();
        assert psi != null : "Bad chameleon: " + chameleon;
        return doParseContents(chameleon, psi);
    }

    @Override
    protected ASTNode doParseContents(@NotNull ASTNode chameleon, @NotNull PsiElement psi) {
        final Project project = psi.getProject();
        Language languageForParser = getLanguageForParser(psi);
        final PsiBuilder builder = PsiBuilderFactory.getInstance().createBuilder(project, chameleon, null, languageForParser, chameleon.getChars());
        //final MyPsiParser parser = new MyPsiParser(project);
        final PsiParser parser = LanguageParserDefinitions.INSTANCE.forLanguage(languageForParser).createParser(project);
        ASTNode result = parser.parse(this, builder);//.getFirstChildNode();
        return result;
    }


    @Nullable
    @Override
    public ASTNode createNode(CharSequence text) {
        return new AntlrFileNode(this, text);
    }


}
