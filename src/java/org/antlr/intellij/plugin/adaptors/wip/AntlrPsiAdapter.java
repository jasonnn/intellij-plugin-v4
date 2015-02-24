package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.intellij.plugin.ANTLRv4Language;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by jason on 2/23/15.
 */
public class AntlrPsiAdapter extends AntlrASTAdapter implements PsiElement {
    public AntlrPsiAdapter(IElementType myType, AntlrAST tree) {
        super(myType, tree);
    }

    @Override
    public PsiElement getPsi() {
        return this;
    }

    @Override
    public ASTNode getNode() {
        return tree;
    }


    @NotNull
    @Override
    public Project getProject() throws PsiInvalidElementAccessException {
        throw new UnsupportedOperationException("TODO");

    }

    @NotNull
    @Override
    public Language getLanguage() {
        return ANTLRv4Language.INSTANCE;
    }

    @Override
    public PsiManager getManager() {
        throw new UnsupportedOperationException("TODO");

    }

    @NotNull
    @Override
    public PsiElement[] getChildren() {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public PsiElement getParent() {
        return (PsiElement) getTreeParent();
    }

    @Override
    public PsiElement getFirstChild() {
        return (PsiElement) getFirstChildNode();
    }

    @Override
    public PsiElement getLastChild() {
        return (PsiElement) getLastChildNode();
    }

    @Override
    public PsiElement getNextSibling() {
        return (PsiElement) getTreeNext();
    }

    @Override
    public PsiElement getPrevSibling() {
        return (PsiElement) getTreePrev();
    }

    @Override
    public PsiFile getContainingFile() throws PsiInvalidElementAccessException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int getStartOffsetInParent() {
        throw new UnsupportedOperationException("TODO");
    }

    @Nullable
    @Override
    public PsiElement findElementAt(int offset) {
        throw new UnsupportedOperationException("TODO");
    }

    @Nullable
    @Override
    public PsiReference findReferenceAt(int offset) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int getTextOffset() {
        throw new UnsupportedOperationException("TODO");
    }

    @NotNull
    @Override
    public char[] textToCharArray() {
        return getText().toCharArray();
    }

    @Override
    public PsiElement getNavigationElement() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public PsiElement getOriginalElement() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean textMatches(@NotNull CharSequence text) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean textMatches(@NotNull PsiElement element) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public void acceptChildren(@NotNull PsiElementVisitor visitor) {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public PsiElement copy() {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public PsiElement addBefore(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public PsiElement addAfter(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void checkAdd(@NotNull PsiElement element) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public PsiElement addRangeBefore(@NotNull PsiElement first, @NotNull PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public PsiElement addRangeAfter(PsiElement first, PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void delete() throws IncorrectOperationException {

    }

    @Override
    public void checkDelete() throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public PsiElement replace(@NotNull PsiElement newElement) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public boolean isWritable() {
        throw new UnsupportedOperationException("TODO");

    }

    @Nullable
    @Override
    public PsiReference getReference() {
        throw new UnsupportedOperationException("TODO");

    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @Nullable PsiElement lastParent, @NotNull PsiElement place) {
        return false;
    }

    @Nullable
    @Override
    public PsiElement getContext() {
        throw new UnsupportedOperationException("TODO");

    }

    @Override
    public boolean isPhysical() {
        throw new UnsupportedOperationException("TODO");

    }

    @NotNull
    @Override
    public GlobalSearchScope getResolveScope() {
        throw new UnsupportedOperationException("TODO");

    }

    @NotNull
    @Override
    public SearchScope getUseScope() {
        throw new UnsupportedOperationException("TODO");
    }


    @Override
    public boolean isEquivalentTo(PsiElement another) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Icon getIcon(int flags) {
        return null;
    }

    @NotNull
    @Override
    public AntlrPsiAdapter clone() {
        //throw new UnsupportedOperationException("todo!!");
         return (AntlrPsiAdapter) super.clone();
    }
}
