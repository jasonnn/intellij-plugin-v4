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
import java.awt.*;

/**
 * Created by jason on 2/10/15.
 *
 * @see com.intellij.json.formatter.JsonCodeStylePanel
 */
public class AntlrCodeStylePanel extends CodeStyleAbstractPanel {

    static final String SAMPLE = "grammar Basics_Rules;\n" +
            "\n" +
            "//every rule should get it's own line.\n" +
            "A : [aA]; B : [bB]; C:[cC];\n" +
            "\n" +
            "//vertical alignment:\n" +
            "a : A;\n" +
            "bb : B B;\n" +
            "ccc : C C C;";

    JPanel myPanel;

    protected AntlrCodeStylePanel(@NotNull CodeStyleSettings settings) {
        super(ANTLRv4Language.INSTANCE, null, settings);
        myPanel = new JPanel(new BorderLayout());
        myPanel.add(new JLabel("todo"), BorderLayout.CENTER);

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
        System.out.println("AntlrCodeStylePanel.apply");
    }

    @Override
    public boolean isModified(CodeStyleSettings settings) {
        return false;
    }

    @Nullable
    @Override
    public JComponent getPanel() {
        return myPanel;
    }

    @Override
    protected void resetImpl(CodeStyleSettings settings) {
        System.out.println("AntlrCodeStylePanel.resetImpl");
    }
}
