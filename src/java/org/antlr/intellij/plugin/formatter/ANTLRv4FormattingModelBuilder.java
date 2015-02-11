package org.antlr.intellij.plugin.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/*          notes:

resources:
    https://confluence.jetbrains.com/display/IntelliJIDEA/Formatter
    https://confluence.jetbrains.com/display/IDEADEV/Developing+Custom+Language+Plugins+for+IntelliJ+IDEA#DevelopingCustomLanguagePluginsforIntelliJIDEA-CodeFormatter


/Users/jason/repos/java/ide/intellij/intellij-community/json/tests/testData/formatting
/Users/jason/repos/java/ide/intellij/intellij-community/json/tests/test/com/intellij/json/JsonFormattingTest.java
 */

/**
 * Created by jason on 2/10/15.
 * @see com.intellij.json.formatter.JsonFormattingBuilderModel
 */
public class ANTLRv4FormattingModelBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        AntlrBlock block = new AntlrBlock(element.getNode(),null,null, Indent.getNoneIndent(),null,settings);
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(),block,settings);

    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }

    static SpacingBuilder createSpacingBuilder(CodeStyleSettings settings){
        return new SpacingBuilder(settings, ANTLRv4Language.INSTANCE);
    }
}
