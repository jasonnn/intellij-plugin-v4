package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.tree.IElementType;

/**
 * Created by jason on 2/24/15.
 */
public class MyFileNode2 extends FileElement {
    public MyFileNode2(IElementType type, CharSequence text) {
        super(type, text);
    }
}
