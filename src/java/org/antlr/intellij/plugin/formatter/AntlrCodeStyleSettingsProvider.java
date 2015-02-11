package org.antlr.intellij.plugin.formatter;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/10/15.
 * @see  com.intellij.json.formatter.JsonCodeStyleSettingsProvider
 */
public class AntlrCodeStyleSettingsProvider  extends CodeStyleSettingsProvider {
    @NotNull
    @Override
    public Configurable createSettingsPage(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
        return new CodeStyleAbstractConfigurable(settings,originalSettings,"antlr"){
            @Override
            protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
                final CodeStyleSettings currentSettings = getCurrentSettings();
                return new TabbedLanguageCodeStylePanel(ANTLRv4Language.INSTANCE, currentSettings, settings) {
                    @Override
                    protected void initTabs(CodeStyleSettings settings) {
                        addIndentOptionsTab(settings);
                        addSpacesTab(settings);
                        addBlankLinesTab(settings);
                        addWrappingAndBracesTab(settings);
                        addTab(new AntlrCodeStylePanel(settings));
                    }
                };
            }
            @Nullable
            @Override
            public String getHelpTopic() {
                return "reference.settingsdialog.codestyle.antlr";
            }

        };
    }
    @Nullable
    @Override
    public String getConfigurableDisplayName() {
        return ANTLRv4Language.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public CustomCodeStyleSettings createCustomSettings(CodeStyleSettings settings) {
        return new AntlrCodeStyleSettings(settings);
    }
}
