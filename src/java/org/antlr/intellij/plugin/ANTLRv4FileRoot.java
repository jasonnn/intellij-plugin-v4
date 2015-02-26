package org.antlr.intellij.plugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubTree;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.keyFMap.KeyFMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class ANTLRv4FileRoot extends PsiFileBase {
    public ANTLRv4FileRoot(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, ANTLRv4Language.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ANTLRv4FileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "ANTLR v4 grammar file";
    }

    @Override
    public Icon getIcon(int flags) {
		return Icons.FILE;
    }

	@NotNull
	@Override
	public PsiElement[] getChildren() {
		return super.getChildren();
	}


    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        super.accept(visitor);
    }

    @NotNull
    @Override
    public ParserDefinition getParserDefinition() {
        return super.getParserDefinition();
    }

    @Override
    public void setContentElementType(IElementType contentElementType) {
        super.setContentElementType(contentElementType);
    }

    @Override
    public IElementType getContentElementType() {
        return super.getContentElementType();
    }

    @Override
    protected void init(@NotNull IElementType elementType, IElementType contentElementType) {
        super.init(elementType, contentElementType);
    }

    @Override
    public TreeElement createContentLeafElement(CharSequence leafText) {
        return super.createContentLeafElement(leafText);
    }

    @Override
    public boolean isDirectory() {
        return super.isDirectory();
    }

    @Override
    public FileElement getTreeElement() {
        return super.getTreeElement();
    }

    @Override
    public VirtualFile getVirtualFile() {
        return super.getVirtualFile();
    }

    @Override
    public boolean processChildren(PsiElementProcessor<PsiFileSystemItem> processor) {
        return super.processChildren(processor);
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    protected boolean isPsiUpToDate(@NotNull VirtualFile vFile) {
        return super.isPsiUpToDate(vFile);
    }

    @Override
    public boolean isContentsLoaded() {
        return super.isContentsLoaded();
    }

    @Override
    public ASTNode findTreeForStub(StubTree tree, StubElement<?> stub) {
        return super.findTreeForStub(tree, stub);
    }

    @Override
    protected void reportStubAstMismatch(String message, StubTree stubTree, Document cachedDocument) {
        super.reportStubAstMismatch(message, stubTree, cachedDocument);
    }

    @NotNull
    @Override
    protected FileElement createFileElement(CharSequence docText) {
        return super.createFileElement(docText);
    }

    @Override
    public void unloadContent() {
        super.unloadContent();
    }

    @Override
    public void clearCaches() {
        super.clearCaches();
    }

    @Override
    public String getText() {
        return super.getText();
    }

    @Override
    public int getTextLength() {
        return super.getTextLength();
    }

    @Override
    public TextRange getTextRange() {
        return super.getTextRange();
    }

    @Override
    public PsiElement getNextSibling() {
        return super.getNextSibling();
    }

    @Override
    public PsiElement getPrevSibling() {
        return super.getPrevSibling();
    }

    @Override
    public long getModificationStamp() {
        return super.getModificationStamp();
    }

    @Override
    public void subtreeChanged() {
        super.subtreeChanged();
    }

    @Override
    protected PsiFileImpl clone() {
        return super.clone();
    }

    @NotNull
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        return super.setName(name);
    }

    @Override
    public void checkSetName(String name) throws IncorrectOperationException {
        super.checkSetName(name);
    }

    @Override
    public boolean isWritable() {
        return super.isWritable();
    }

    @Override
    public PsiDirectory getParent() {
        return super.getParent();
    }

    @Nullable
    @Override
    public PsiDirectory getContainingDirectory() {
        return super.getContainingDirectory();
    }

    @NotNull
    @Override
    public PsiFile getContainingFile() {
        return super.getContainingFile();
    }

    @Override
    public void delete() throws IncorrectOperationException {
        super.delete();
    }

    @Override
    public void checkDelete() throws IncorrectOperationException {
        super.checkDelete();
    }

    @NotNull
    @Override
    public PsiFile getOriginalFile() {
        return super.getOriginalFile();
    }

    @Override
    public void setOriginalFile(@NotNull PsiFile originalFile) {
        super.setOriginalFile(originalFile);
    }

    @NotNull
    @Override
    public PsiFile[] getPsiRoots() {
        return super.getPsiRoots();
    }

    @Override
    public boolean isPhysical() {
        return super.isPhysical();
    }

    @NotNull
    @Override
    public FileViewProvider getViewProvider() {
        return super.getViewProvider();
    }

    @Override
    public void setTreeElementPointer(FileElement element) {
        super.setTreeElementPointer(element);
    }

    @Override
    public PsiElement findElementAt(int offset) {
        return super.findElementAt(offset);
    }

    @Override
    public PsiReference findReferenceAt(int offset) {
        return super.findReferenceAt(offset);
    }

    @NotNull
    @Override
    public char[] textToCharArray() {
        return super.textToCharArray();
    }

    @NotNull
    @Override
    public <T> T[] findChildrenByClass(Class<T> aClass) {
        return super.findChildrenByClass(aClass);
    }

    @Nullable
    @Override
    public <T> T findChildByClass(Class<T> aClass) {
        return super.findChildByClass(aClass);
    }

    @Override
    public boolean isTemplateDataFile() {
        return super.isTemplateDataFile();
    }

    @Override
    public PsiElement getContext() {
        return super.getContext();
    }

    @Override
    public void onContentReload() {
        super.onContentReload();
    }

    @Nullable
    @Override
    public StubElement getStub() {
        return super.getStub();
    }

    @Nullable
    @Override
    public StubTree getStubTree() {
        return super.getStubTree();
    }

    @Override
    protected PsiFileImpl cloneImpl(FileElement treeElementClone) {
        return super.cloneImpl(treeElementClone);
    }

    @Override
    public PsiManager getManager() {
        return super.getManager();
    }

    @Override
    public PsiElement getNavigationElement() {
        return super.getNavigationElement();
    }

    @Override
    public PsiElement getOriginalElement() {
        return super.getOriginalElement();
    }

    @Override
    public PsiElement getFirstChild() {
        return super.getFirstChild();
    }

    @Override
    public PsiElement getLastChild() {
        return super.getLastChild();
    }

    @Override
    public void acceptChildren(@NotNull PsiElementVisitor visitor) {
        super.acceptChildren(visitor);
    }

    @Override
    public int getStartOffsetInParent() {
        return super.getStartOffsetInParent();
    }

    @Override
    public int getTextOffset() {
        return super.getTextOffset();
    }

    @Override
    public boolean textMatches(@NotNull CharSequence text) {
        return super.textMatches(text);
    }

    @Override
    public boolean textMatches(@NotNull PsiElement element) {
        return super.textMatches(element);
    }

    @Override
    public boolean textContains(char c) {
        return super.textContains(c);
    }

    @Override
    public PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
        return super.add(element);
    }

    @Override
    public PsiElement addBefore(@NotNull PsiElement element, PsiElement anchor) throws IncorrectOperationException {
        return super.addBefore(element, anchor);
    }

    @Override
    public PsiElement addAfter(@NotNull PsiElement element, PsiElement anchor) throws IncorrectOperationException {
        return super.addAfter(element, anchor);
    }

    @Override
    public PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        return super.addRange(first, last);
    }

    @Override
    public PsiElement addRangeBefore(@NotNull PsiElement first, @NotNull PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        return super.addRangeBefore(first, last, anchor);
    }

    @Override
    public PsiElement addRangeAfter(PsiElement first, PsiElement last, PsiElement anchor) throws IncorrectOperationException {
        return super.addRangeAfter(first, last, anchor);
    }

    @Override
    public void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        super.deleteChildRange(first, last);
    }

    @Override
    public PsiElement replace(@NotNull PsiElement newElement) throws IncorrectOperationException {
        return super.replace(newElement);
    }

    @Override
    public PsiReference getReference() {
        return super.getReference();
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return super.getReferences();
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        return super.processDeclarations(processor, state, lastParent, place);
    }

    @NotNull
    @Override
    public GlobalSearchScope getResolveScope() {
        return super.getResolveScope();
    }

    @NotNull
    @Override
    public SearchScope getUseScope() {
        return super.getUseScope();
    }

    @Override
    public ItemPresentation getPresentation() {
        return super.getPresentation();
    }

    @Override
    public void navigate(boolean requestFocus) {
        super.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return super.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return super.canNavigateToSource();
    }

    @NotNull
    @Override
    public Project getProject() {
        return super.getProject();
    }

    @NotNull
    @Override
    public FileASTNode getNode() {
        return super.getNode();
    }

    @Override
    public boolean isEquivalentTo(PsiElement another) {
        return super.isEquivalentTo(another);
    }

    @Override
    public StubTree calcStubTree() {
        return super.calcStubTree();
    }

    @Override
    public void putInfo(@NotNull Map<String, String> info) {
        super.putInfo(info);
    }

    @Override
    protected Icon computeBaseIcon(@IconFlags int flags) {
        return super.computeBaseIcon(flags);
    }

    @Override
    protected Icon getBaseIcon() {
        return super.getBaseIcon();
    }

    @Override
    protected Icon getAdjustedBaseIcon(Icon icon, @IconFlags int flags) {
        return super.getAdjustedBaseIcon(icon, flags);
    }

    @Override
    protected boolean isVisibilitySupported() {
        return super.isVisibilitySupported();
    }

    @Nullable
    @Override
    protected Icon getElementIcon(@IconFlags int flags) {
        return super.getElementIcon(flags);
    }

    @Override
    public String getUserDataString() {
        return super.getUserDataString();
    }

    @Override
    public void copyUserDataTo(UserDataHolderBase other) {
        super.copyUserDataTo(other);
    }

    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return super.getUserData(key);
    }

    @NotNull
    @Override
    protected KeyFMap getUserMap() {
        return super.getUserMap();
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, T value) {
        super.putUserData(key, value);
    }

    @Override
    protected boolean changeUserMap(KeyFMap oldMap, KeyFMap newMap) {
        return super.changeUserMap(oldMap, newMap);
    }

    @Override
    public <T> T getCopyableUserData(Key<T> key) {
        return super.getCopyableUserData(key);
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, T value) {
        super.putCopyableUserData(key, value);
    }

    @Override
    public <T> boolean replace(@NotNull Key<T> key, T oldValue, T newValue) {
        return super.replace(key, oldValue, newValue);
    }

    @NotNull
    @Override
    public <T> T putUserDataIfAbsent(@NotNull Key<T> key, @NotNull T value) {
        return super.putUserDataIfAbsent(key, value);
    }

    @Override
    public void copyCopyableDataTo(@NotNull UserDataHolderBase clone) {
        super.copyCopyableDataTo(clone);
    }

    @Override
    protected void clearUserData() {
        super.clearUserData();
    }

    @Override
    protected void setUserMap(KeyFMap map) {
        super.setUserMap(map);
    }

    @Override
    public boolean isUserDataEmpty() {
        return super.isUserDataEmpty();
    }
}
