package org.antlr.intellij.adaptor.ast;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by jason on 2/24/15.
 */
public interface SiblingIndexParseTree extends ParseTree {
    int getSiblingIndex();
}
