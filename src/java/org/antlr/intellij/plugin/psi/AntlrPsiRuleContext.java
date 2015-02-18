package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * bummer: clash between ParserRuleContext.getParent & PsiElement.getParent;
 * Created by jason on 2/16/15.
 */

public class AntlrPsiRuleContext extends AntlrASTRuleContext /*implements PsiElement*/ {
    public AntlrPsiRuleContext(IElementType myType) {
        super(myType);
    }

    @NotNull

    public Project getProject() throws PsiInvalidElementAccessException {
        return null;
    }

    @NotNull

    public Language getLanguage() {
        return null;
    }


    public PsiManager getManager() {
        return null;
    }

    @NotNull

    public PsiElement[] getChildren() {
        return new PsiElement[0];
    }


    public PsiElement getFirstChild() {
        return null;
    }


    public PsiElement getLastChild() {
        return null;
    }


    public PsiElement getNextSibling() {
        return null;
    }


    public PsiElement getPrevSibling() {
        return null;
    }


    public PsiFile getContainingFile() throws PsiInvalidElementAccessException {
        return null;
    }


    public int getStartOffsetInParent() {
        return 0;
    }

    @Nullable

    public PsiElement findElementAt(int offset) {
        return null;
    }

    @Nullable

    public PsiReference findReferenceAt(int offset) {
        return null;
    }


    public int getTextOffset() {
        return 0;
    }

    @NotNull

    public char[] textToCharArray() {
        return new char[0];
    }


    public PsiElement getNavigationElement() {
        return null;
    }


    public PsiElement getOriginalElement() {
        return null;
    }


    public boolean textMatches(@NotNull CharSequence text) {
        return false;
    }


    public boolean textMatches(@NotNull PsiElement element) {
        return false;
    }


    public void accept(@NotNull PsiElementVisitor visitor) {

    }


    public void acceptChildren(@NotNull PsiElementVisitor visitor) {

    }


    public PsiElement copy() {
        return null;
    }


    public PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
        return null;
    }


    public PsiElement addBefore(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        return null;
    }


    public PsiElement addAfter(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
        return null;
    }


    public void checkAdd(@NotNull PsiElement element) throws IncorrectOperationException {

    }


    public PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        return null;
    }


    public PsiElement addRangeBefore(@NotNull PsiElement first, @NotNull PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        return null;
    }


    public PsiElement addRangeAfter(PsiElement first, PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        return null;
    }


    public void delete() throws IncorrectOperationException {

    }


    public void checkDelete() throws IncorrectOperationException {

    }


    public void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {

    }


    public PsiElement replace(@NotNull PsiElement newElement) throws IncorrectOperationException {
        return null;
    }


    public boolean isValid() {
        return false;
    }


    public boolean isWritable() {
        return false;
    }

    @Nullable

    public PsiReference getReference() {
        return null;
    }

    @NotNull

    public PsiReference[] getReferences() {
        return new PsiReference[0];
    }


    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @Nullable PsiElement lastParent, @NotNull PsiElement place) {
        return false;
    }

    @Nullable

    public PsiElement getContext() {
        return null;
    }


    public boolean isPhysical() {
        return false;
    }

    @NotNull

    public GlobalSearchScope getResolveScope() {
        return null;
    }

    @NotNull

    public SearchScope getUseScope() {
        return null;
    }


    public ASTNode getNode() {
        return null;
    }


    public boolean isEquivalentTo(PsiElement another) {
        return false;
    }


    public Icon getIcon(int flags) {
        return null;
    }
}
