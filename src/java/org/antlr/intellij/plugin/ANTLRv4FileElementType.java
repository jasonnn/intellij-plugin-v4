package org.antlr.intellij.plugin;

import com.intellij.lang.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IFileElementType;
import org.antlr.intellij.plugin.adaptors.wip.MyAntlrFileNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/25/15.
 */
public class ANTLRv4FileElementType extends IFileElementType {
    public static ANTLRv4FileElementType INSTANCE = new ANTLRv4FileElementType();

    private ANTLRv4FileElementType() {
        super("ANTLRv4_File", ANTLRv4Language.INSTANCE, true);
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
        return parser.parse(this, builder).getFirstChildNode();
    }


    @Nullable
    @Override
    public ASTNode createNode(CharSequence text) {
        return new MyAntlrFileNode(this, text);
    }


}
