package org.antlr.intellij.plugin.formatter;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

/**
 * Created by jason on 2/10/15.
 * @see com.intellij.json.formatter.JsonCodeStyleSettings;

 */
public class AntlrCodeStyleSettings extends CustomCodeStyleSettings {
    protected AntlrCodeStyleSettings(String tagName, CodeStyleSettings container) {
        super(tagName, container);
    }
}
