package org.antlr.intellij.adaptor.ast;

import com.intellij.lang.*;
import com.intellij.openapi.util.Getter;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.CharTableImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.ILightStubFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.CharTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jason on 3/1/15.
 */
public abstract class AbstractFileNode extends UserDataHolderBase implements FileASTNode, Getter<FileASTNode> {

    private volatile CharTable myCharTable = new CharTableImpl();
    private volatile boolean myDetached;

    // Lock which protects expanding chameleon for this node.
    // Under no circumstances should you grab the PSI_LOCK while holding this lock.
    private final Object lock = new Object();
    /** guarded by {@link #lock} */
    private CharSequence myText;

    private static final ThreadLocal<Boolean> ourSuppressEagerPsiCreation = new ThreadLocal<Boolean>();

   // final IElementType myElementType;

    public AbstractFileNode(IElementType type, CharSequence text) {
       // super(type, text);
    }

   // @Override
    protected PsiElement createPsiNoLock() {
        throw new UnsupportedOperationException("TODO");
      //  return myDetached ? null : super.createPsiNoLock();
    }

    public void detachFromFile() {
        myDetached = true;
       //learPsi();
    }

    @Override
    @NotNull
    public CharTable getCharTable() {
        return myCharTable;
    }

    @Nullable
    @Override
    public LighterAST getLighterAST() {
        final IFileElementType contentType = (IFileElementType)getElementType();
        assert contentType instanceof ILightStubFileElementType :contentType;

        LighterAST tree;
        if (!isParsed()) {
            return new FCTSBackedLighterAST(getCharTable(), ((ILightStubFileElementType<?>)contentType).parseContentsLight(this));
        }
        else {
            tree = new TreeBackedLighterAST(this);
        }
        return tree;
    }




    @NotNull
    @Override
    public Object clone() {
        throw new UnsupportedOperationException("TODO");
    }
}
