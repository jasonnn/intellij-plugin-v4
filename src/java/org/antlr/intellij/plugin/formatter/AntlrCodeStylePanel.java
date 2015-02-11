package org.antlr.intellij.plugin.formatter;

import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.testFramework.LightVirtualFile;
import org.antlr.intellij.plugin.ANTLRv4FileType;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by jason on 2/10/15.
 * @see com.intellij.json.formatter.JsonCodeStylePanel
 */
public class AntlrCodeStylePanel extends CodeStyleAbstractPanel {

    static final String SAMPLE = "grammar todo;";

    protected AntlrCodeStylePanel(@NotNull CodeStyleSettings settings) {
        super(ANTLRv4Language.INSTANCE, null, settings);
    }

    @Override
    protected int getRightMargin() {
        return 80;
    }

    @Nullable
    @Override
    protected EditorHighlighter createHighlighter(EditorColorsScheme scheme) {
        return EditorHighlighterFactory.getInstance().createEditorHighlighter(new LightVirtualFile("a.g4"), scheme, null);
    }

    @NotNull
    @Override
    protected FileType getFileType() {
        return ANTLRv4FileType.INSTANCE;
    }

    @Nullable
    @Override
    protected String getPreviewText() {
        return SAMPLE;
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
