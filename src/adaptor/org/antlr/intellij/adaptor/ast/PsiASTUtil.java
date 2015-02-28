package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.ArrayFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 2/25/15.
 */
public class PsiASTUtil {
    private static final Logger LOG = Logger.getInstance("#org.antlr.intellij.adaptor.ast.PsiASTUtil");


    @SuppressWarnings("unchecked")
    @NotNull
    public static <T extends PsiElement> T[] getChildrenAsPsiElements(ASTNode parentNode, @Nullable TokenSet filter) {
        return (T[]) getChildrenAsPsiElements(parentNode, filter, PsiElement.ARRAY_FACTORY);
    }

    @NotNull
    public static <T extends PsiElement> T[] getChildrenAsPsiElements(ASTNode parentNode, @Nullable TokenSet filter, ArrayFactory<T> constructor) {
        ApplicationManager.getApplication().assertReadAccessAllowed();
        int count = countChildren(parentNode, filter);
        T[] result = constructor.create(count);
        if (count == 0) {
            return result;
        }
        int idx = 0;
        for (ASTNode child = parentNode.getFirstChildNode(); child != null && idx < count; child = child.getTreeNext()) {
            if (filter == null || filter.contains(child.getElementType())) {
                @SuppressWarnings("unchecked") T element = (T) child.getPsi();
                LOG.assertTrue(element != null, child);
                result[idx++] = element;
            }
        }
        return result;
    }

    public static int countChildren(ASTNode parent, @Nullable TokenSet filter) {
        // no lock is needed because all chameleons are expanded already
        int count = 0;
        for (ASTNode child = parent.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            if (filter == null || filter.contains(child.getElementType())) {
                count++;
            }
        }

        return count;
    }

    public static int countChildren(ASTNode parent, IElementType type) {
        // no lock is needed because all chameleons are expanded already
        int count = 0;
        for (ASTNode child = parent.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            if (type == child.getElementType()) {
                count++;
            }
        }

        return count;
    }
}
