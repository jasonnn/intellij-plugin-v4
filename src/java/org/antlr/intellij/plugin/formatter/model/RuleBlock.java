package org.antlr.intellij.plugin.formatter.model;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/11/15.
 */
public class RuleBlock extends AntlrBlock{
    protected RuleBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, @NotNull Indent indent, @Nullable AntlrBlock parent, @NotNull CodeStyleSettings settings) {
        super(node, wrap, alignment, indent, parent, settings);
    }
}
