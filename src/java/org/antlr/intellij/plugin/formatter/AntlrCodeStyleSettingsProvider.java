package org.antlr.intellij.plugin.formatter;

import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jason on 2/10/15.
 * @see  com.intellij.json.formatter.JsonCodeStyleSettingsProvider
 */
public class AntlrCodeStyleSettingsProvider  extends CodeStyleSettingsProvider {
    @NotNull
    @Override
    public Configurable createSettingsPage(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
        return null;
    }
}
