package org.antlr.intellij.plugin.formatter;

import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jason on 2/10/15.
 * @see com.intellij.json.formatter.JsonLanguageCodeStyleSettingsProvider
 */
public class AntlrLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    @NotNull
    @Override
    public Language getLanguage() {
        return null;
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return null;
    }
}
