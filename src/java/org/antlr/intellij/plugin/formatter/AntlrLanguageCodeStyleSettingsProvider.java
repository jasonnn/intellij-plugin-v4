package org.antlr.intellij.plugin.formatter;

import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable.SPACES_OTHER;

/**
 * Created by jason on 2/10/15.
 *
 * @see com.intellij.json.formatter.JsonLanguageCodeStyleSettingsProvider
 */
public class AntlrLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    private static final String SAMPLE = "grammar Basics_Rules;\n" +
            "\n" +
            "//every rule should get it's own line.\n" +
            "A : [aA]; B : [bB]; C:[cC];\n" +
            "\n" +
            "//vertical alignment:\n" +
            "a : A;\n" +
            "bb : B B;\n" +
            "ccc : C C C;";


    @NotNull
    @Override
    public Language getLanguage() {
        return ANTLRv4Language.INSTANCE;
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return SAMPLE;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
      //TODO: this is for json
        if (settingsType == SettingsType.SPACING_SETTINGS) {
            consumer.showStandardOptions("SPACE_WITHIN_BRACKETS",
                    "SPACE_WITHIN_BRACES",
                    "SPACE_AFTER_COMMA",
                    "SPACE_BEFORE_COMMA");
            consumer.renameStandardOption("SPACE_WITHIN_BRACES", "Braces");
            consumer.showCustomOption(AntlrCodeStyleSettings.class, "SPACE_BEFORE_COLON", "Before ':'", SPACES_OTHER);
            consumer.showCustomOption(AntlrCodeStyleSettings.class, "SPACE_AFTER_COLON", "After ':'", SPACES_OTHER);
        }
        else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
            consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE");
        }
        else if (settingsType == SettingsType.WRAPPING_AND_BRACES_SETTINGS) {
            consumer.showStandardOptions("RIGHT_MARGIN",
                    "KEEP_LINE_BREAKS",
                    "WRAP_LONG_LINES");

//            consumer.showCustomOption(AntlrCodeStyleSettings.class,
//                    "ARRAY_WRAPPING",
//                    "Arrays",
//                    null,
//                    CodeStyleSettingsCustomizable.WRAP_OPTIONS,
//                    CodeStyleSettingsCustomizable.WRAP_VALUES);

//            consumer.showCustomOption(AntlrCodeStyleSettings.class,
//                    "OBJECT_WRAPPING",
//                    "Objects",
//                    null,
//                    CodeStyleSettingsCustomizable.WRAP_OPTIONS,
//                    CodeStyleSettingsCustomizable.WRAP_VALUES);

        }
    }

    @Nullable
    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

    @Nullable
    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings commonSettings = new CommonCodeStyleSettings(getLanguage());
        CommonCodeStyleSettings.IndentOptions indentOptions = commonSettings.initIndentOptions();
        indentOptions.INDENT_SIZE = 2;
        return commonSettings;
    }
}
