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
 *
 * @see com.intellij.json.formatter.JsonCodeStyleSettingsProvider
 */
public class AntlrCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    static class AntlrSettingsPage extends CodeStyleAbstractConfigurable {
        AntlrSettingsPage(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
            super(settings, originalSettings, "ANTLRv4");
        }

        @Override
        protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
            return new AntlrSettingsPanel(getCurrentSettings(), settings);
        }

        @Nullable
        @Override
        public String getHelpTopic() {
            return "reference.settingsdialog.codestyle.antlr";
        }
    }

    static class AntlrSettingsPanel extends TabbedLanguageCodeStylePanel {
        AntlrSettingsPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(ANTLRv4Language.INSTANCE, currentSettings, settings);
        }

        @Override
        protected void initTabs(CodeStyleSettings settings) {
            addIndentOptionsTab(settings);
            addSpacesTab(settings);
            addBlankLinesTab(settings);
            addWrappingAndBracesTab(settings);
            addTab(new AntlrCodeStylePanel(settings));
        }
    }

    @NotNull
    @Override
    public Configurable createSettingsPage(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
        return new AntlrSettingsPage(settings, originalSettings);
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
