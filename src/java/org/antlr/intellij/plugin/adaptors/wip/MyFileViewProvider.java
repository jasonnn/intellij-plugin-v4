package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import org.antlr.intellij.plugin.ANTLRv4Language;

/**
 * Created by jason on 2/24/15.
 */
public class MyFileViewProvider extends SingleRootFileViewProvider {
    protected MyFileViewProvider(PsiManager manager, VirtualFile virtualFile, boolean eventSystemEnabled) {
        super(manager, virtualFile, eventSystemEnabled, ANTLRv4Language.INSTANCE);
    }
}
