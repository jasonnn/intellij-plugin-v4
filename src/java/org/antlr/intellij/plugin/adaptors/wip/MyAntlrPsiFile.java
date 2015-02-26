package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.FileASTNode;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.PsiFileEx;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jason on 2/25/15.
 */
public class MyAntlrPsiFile extends MyAbstractPsiFile implements PsiFileEx {

    protected MyAntlrPsiFile(@NotNull IElementType elementType, IElementType contentElementType, @NotNull FileViewProvider provider) {
        super(elementType, contentElementType, provider, ANTLRv4Language.INSTANCE);
    }

    public MyAntlrPsiFile(@NotNull FileViewProvider provider) {
        super(provider, ANTLRv4Language.INSTANCE);
    }


    @NotNull
    @Override
    protected FileASTNode createFileASTNode(CharSequence docText) {
        // return new MyAntlrFileNode();
        return super.createFileASTNode(docText);
    }
}
