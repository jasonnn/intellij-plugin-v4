package org.antlr.intellij.plugin.formatter;

import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.json.JsonLanguage;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/10/15.
 *
 * @see com.intellij.json.formatter.JsonLanguageCodeStyleSettingsProvider
 */
public class AntlrLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    private static final String SAMPLE = "/*todo*/" + "grammar Todo;";


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
        super.customizeSettings(consumer, settingsType);
    }

    @Nullable
    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

    @Nullable
    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings commonSettings = new CommonCodeStyleSettings(JsonLanguage.INSTANCE);
        // CommonCodeStyleSettings.IndentOptions indentOptions = commonSettings.initIndentOptions();
        //  indentOptions.INDENT_SIZE = 2;

        return commonSettings;
    }
}
