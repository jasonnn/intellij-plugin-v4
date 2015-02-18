package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;

/**
 * Created by jason on 2/16/15.
 */
public class ASTNodeHelper {
    public static boolean textContains(ASTNode node, char c) {
        CharSequence chars = node.getChars();
        for (int i = 0; i < chars.length(); i++) {
            if (chars.charAt(i) == c) return true;
        }
        return false;
    }

    public static int getTextLength(ASTNode node) {
        return node.getChars().length();
    }

    public static TextRange getTextRange(ASTNode node) {
        return TextRange.from(node.getStartOffset(), node.getTextLength());
    }

    public static ASTNode getTreeNext(ASTNode node) {
        return null;
    }
}
