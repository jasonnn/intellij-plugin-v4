package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.PsiFileEx;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.plugin.ANTLRv4FileType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jason on 2/25/15.
 */
public class MyAntlrPsiFile extends MyAbstractPsiFile implements PsiFileEx {

    protected MyAntlrPsiFile(@NotNull IElementType elementType, IElementType contentElementType, @NotNull FileViewProvider provider) {
        super(elementType, contentElementType, provider);
    }

    public MyAntlrPsiFile(@NotNull FileViewProvider provider) {
        super(provider);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ANTLRv4FileType.INSTANCE;
    }


}
