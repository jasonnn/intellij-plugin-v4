package org.antlr.intellij.plugin.adaptors.wip;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by jason on 2/24/15.
 */
public interface MyParseTree extends ParseTree {
    int getSiblingIndex();
}
