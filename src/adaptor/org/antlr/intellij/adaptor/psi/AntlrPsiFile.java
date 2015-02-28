package org.antlr.intellij.adaptor.psi;

import com.intellij.lang.FileASTNode;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.PsiFileEx;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.ast.AntlrFileNode;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jason on 2/25/15.
 *
 * note that for normal psi elements, the ast is the one responsible for creating the psi.
 * for files though, this is reversed.
 */
public class AntlrPsiFile extends AbstractAntlrPsiFile implements PsiFileEx {

    protected AntlrPsiFile(@NotNull IElementType elementType, IElementType contentElementType, @NotNull FileViewProvider provider) {
        super(elementType, contentElementType, provider, ANTLRv4Language.INSTANCE);
    }

    public AntlrPsiFile(@NotNull FileViewProvider provider) {
        super(provider, ANTLRv4Language.INSTANCE);
    }


    @NotNull
    @Override
    protected FileASTNode createFileASTNode(CharSequence docText) {
        AntlrFileNode node = new AntlrFileNode(getContentElementType(), docText);
        node.setPsi(this);
        return node;
    }

    @Override
    public String toString() {
        final VirtualFile virtualFile = getVirtualFile();
        return "ANTLRv4File: " + (virtualFile != null ? virtualFile.getName() : "<unknown>");
    }
}
