package org.antlr.intellij.plugin.formatter;

import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by jason on 2/10/15.
 * @see com.intellij.json.formatter.JsonCodeStylePanel
 */
public class AntlrCodeStylePanel extends CodeStyleAbstractPanel {
    protected AntlrCodeStylePanel(@NotNull CodeStyleSettings settings) {
        super(settings);
    }

    protected AntlrCodeStylePanel(@Nullable Language defaultLanguage, @Nullable CodeStyleSettings currentSettings, @NotNull CodeStyleSettings settings) {
        super(defaultLanguage, currentSettings, settings);
    }

    @Override
    protected int getRightMargin() {
        return 0;
    }

    @Nullable
    @Override
    protected EditorHighlighter createHighlighter(EditorColorsScheme scheme) {
        return null;
    }

    @NotNull
    @Override
    protected FileType getFileType() {
        return null;
    }

    @Nullable
    @Override
    protected String getPreviewText() {
        return null;
    }

    @Override
    public void apply(CodeStyleSettings settings) throws ConfigurationException {

    }

    @Override
    public boolean isModified(CodeStyleSettings settings) {
        return false;
    }

    @Nullable
    @Override
    public JComponent getPanel() {
        return null;
    }

    @Override
    protected void resetImpl(CodeStyleSettings settings) {

    }
}
