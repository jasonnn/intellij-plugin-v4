package org.antlr.intellij.plugin.adaptors.wip;

import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.lang.ASTFactory;
import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Getter;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.*;
import com.intellij.psi.impl.file.PsiFileImplUtil;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.resolve.FileContextUtil;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.psi.impl.source.tree.TreeElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.ILazyParseableElementType;
import com.intellij.reference.SoftReference;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PatchedWeakReference;
import com.intellij.util.text.CharArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

/**
 * Created by jason on 2/25/15.
 */
public abstract class MyAbstractPsiFile extends PsiElementBase implements PsiFileEx {
    private static final Logger LOG = Logger.getInstance("#org.antlr.intellij.plugin.adaptors.wip.MyAbstractPsiFile");


    private IElementType myElementType;
    protected IElementType myContentElementType;
    private long myModificationStamp;

    protected PsiFile myOriginalFile = null;
    private final FileViewProvider myViewProvider;
    protected final PsiManagerEx myManager;
    // private volatile Getter<FileElement> myTreeElementPointer; // SoftReference/WeakReference to ASTNode or a strong reference to a tree if the file is a DummyHolder

    private volatile Getter<FileASTNode> astNodePointer;


    protected MyAbstractPsiFile(@NotNull IElementType elementType, IElementType contentElementType, @NotNull FileViewProvider provider) {
        this(provider);
        init(elementType, contentElementType);
    }

    protected MyAbstractPsiFile(@NotNull FileViewProvider provider) {
        myManager = (PsiManagerEx) provider.getManager();
        myViewProvider = provider;
    }

    protected void init(@NotNull final IElementType elementType, final IElementType contentElementType) {
        myElementType = elementType;
        setContentElementType(contentElementType);
    }

    @NotNull
    @Override
    public FileASTNode getNode() {
        return calcFileNode();
    }

    protected FileASTNode calcFileNode() {
        // Attempt to find (loaded) tree element without taking lock first.
        FileASTNode fileASTNode = getFileASTNode();
        if (fileASTNode != null) return fileASTNode;

        return loadFileASTNode();
    }


    protected FileASTNode getFileASTNode() {
        FileASTNode node = derefFileASTNoe();
        if (node != null) return node;

        if (!getViewProvider().isPhysical()) {
            return loadFileASTNode();
        }

        return null;
    }

    protected FileASTNode loadFileASTNode() {

        ApplicationManager.getApplication().assertReadAccessAllowed();

        final FileViewProvider viewProvider = getViewProvider();
        if (viewProvider.isPhysical() && myManager.isAssertOnFileLoading(viewProvider.getVirtualFile())) {
            LOG.error("Access to tree elements not allowed in tests. path='" + viewProvider.getVirtualFile().getPresentableUrl() + "'");
        }

        Document cachedDocument = FileDocumentManager.getInstance().getCachedDocument(getViewProvider().getVirtualFile());

        FileASTNode fileNode = createFileElement(viewProvider.getContents());
        // treeElement.setPsi(this);

        synchronized (PsiLock.LOCK) {
            FileASTNode existing = derefFileASTNoe();
            if (existing != null) {
                return existing;
            }
            astNodePointer = createFileASTPointer(fileNode);

            if (LOG.isDebugEnabled() && viewProvider.isPhysical()) {
                LOG.debug("Loaded text for file " + viewProvider.getVirtualFile().getPresentableUrl());
            }

            return fileNode;


        }
    }

    @NotNull
    protected FileASTNode createFileElement(CharSequence docText) {
        throw new UnsupportedOperationException("TODO");
//        final FileASTNode treeElement;
//        final FileASTNode contentLeaf = createContentLeafElement(docText);
//
//        if (contentLeaf instanceof FileElement) {
//            treeElement = (FileElement) contentLeaf;
//        } else {
//            final CompositeElement xxx = ASTFactory.composite(myElementType);
//            assert xxx instanceof FileElement : "BUMM";
//            treeElement = (FileElement) xxx;
//            treeElement.rawAddChildrenWithoutNotifications(contentLeaf);
//        }
//
//        return treeElement;
    }

    public TreeElement createContentLeafElement(CharSequence leafText) {
        if (myContentElementType instanceof ILazyParseableElementType) {
            return ASTFactory.lazy((ILazyParseableElementType) myContentElementType, leafText);
        }
        return ASTFactory.leaf(myContentElementType, leafText);
    }


    private FileASTNode derefFileASTNoe() {
        Getter<FileASTNode> pointer = astNodePointer;
        FileASTNode fileNode = SoftReference.deref(pointer);
        if (fileNode != null) return fileNode;

        synchronized (PsiLock.LOCK) {
            if (astNodePointer == pointer) {
                astNodePointer = null;
            }
        }
        return null;
    }

    @Override
    public boolean isContentsLoaded() {
        return derefFileASTNoe() != null;
    }

    private boolean isKeepTreeElementByHardReference() {
        return !getViewProvider().isEventSystemEnabled();
    }

    @NotNull
    private Getter<FileASTNode> createFileASTPointer(@NotNull FileASTNode fileNode) {
        if (isKeepTreeElementByHardReference()) {
            throw new UnsupportedOperationException("TODO");
            // return Getter fileNode;
        }
        return myManager.isBatchFilesProcessingMode()
                ? new PatchedWeakReference<FileASTNode>(fileNode)
                : new SoftReference<FileASTNode>(fileNode);
    }


    public void setContentElementType(final IElementType contentElementType) {
        LOG.assertTrue(contentElementType instanceof ILazyParseableElementType, contentElementType);
        myContentElementType = contentElementType;
    }

    public IElementType getContentElementType() {
        return myContentElementType;
    }


    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public VirtualFile getVirtualFile() {
        return getViewProvider().isEventSystemEnabled() ? getViewProvider().getVirtualFile() : null;
    }

    @Override
    public boolean processChildren(final PsiElementProcessor<PsiFileSystemItem> processor) {
        return true;
    }

    @Override
    public boolean isValid() {
        FileViewProvider provider = getViewProvider();
        final VirtualFile vFile = provider.getVirtualFile();
        if (!vFile.isValid()) return false;
        if (!provider.isEventSystemEnabled()) return true; // "dummy" file
        if (myManager.getProject().isDisposed()) return false;
        return isPsiUpToDate(vFile);
    }

    protected boolean isPsiUpToDate(@NotNull VirtualFile vFile) {
        final FileViewProvider provider = myManager.findViewProvider(vFile);
        Language language = getLanguage();
        if (provider == null || provider.getPsi(language) == this) { // provider == null in tests
            return true;
        }
        Language baseLanguage = provider.getBaseLanguage();
        return baseLanguage != language && provider.getPsi(baseLanguage) == this;
    }

    @Override
    public String getText() {
        return getViewProvider().getContents().toString();
    }

    @Override
    public int getTextLength() {
        final ASTNode tree = getNode();
        if (tree != null) return tree.getTextLength();

        return getViewProvider().getContents().length();
    }

    @Override
    public TextRange getTextRange() {
        return new TextRange(0, getTextLength());
    }

    @Override
    public long getModificationStamp() {
        return myModificationStamp;
    }

    @Override
    public void subtreeChanged() {
        doClearCaches("subtreeChanged");
        getViewProvider().rootChanged(this);
    }

    public void clearCaches() {
        myModificationStamp++;
    }

    private void doClearCaches(String reason) {
//        final FileElement tree = getTreeElement();
//        if (tree != null) {
//            tree.clearCaches();
//        }
//
//        synchronized (PsiLock.LOCK) {
//            clearStub(reason);
//        }
//        if (tree != null) {
//            tree.putUserData(STUB_TREE_IN_PARSED_TREE, null);
//        }
//
        clearCaches();
    }

    @Override
    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "CloneDoesntCallSuperClone"})
    protected MyAbstractPsiFile clone() {
        throw new UnsupportedOperationException("TODO");
//        FileViewProvider viewProvider = getViewProvider();
//        FileViewProvider providerCopy = viewProvider.clone();
//        final Language language = getLanguage();
//        if (providerCopy == null) {
//            throw new AssertionError("Unable to clone the view provider: " + viewProvider + "; " + language);
//        }
        // MyAbstractPsiFile clone = BlockSupportImpl.getFileCopy(this, providerCopy);
        // copyCopyableDataTo(clone);

//        if (getNode() != null) {
//            // not set by provider in clone
//           // final FileElement treeClone = (FileElement)calcTreeElement().clone();
//           // clone.setTreeElementPointer(treeClone); // should not use setTreeElement here because cloned file still have VirtualFile (SCR17963)
//          //  treeClone.setPsi(clone);
//        }
//
//        if (viewProvider.isEventSystemEnabled()) {
//            clone.myOriginalFile = this;
//        }
//        else if (myOriginalFile != null) {
//            clone.myOriginalFile = myOriginalFile;
//        }
//
//        return clone;
    }

    @Override
    @NotNull
    public String getName() {
        return getViewProvider().getVirtualFile().getName();
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        checkSetName(name);
        doClearCaches("setName");
        return PsiFileImplUtil.setName(this, name);
    }

    @Override
    public void checkSetName(String name) throws IncorrectOperationException {
        if (!getViewProvider().isEventSystemEnabled()) return;
        PsiFileImplUtil.checkSetName(this, name);
    }

    @Override
    public boolean isWritable() {
        return getViewProvider().getVirtualFile().isWritable();
    }

    @Override
    public PsiDirectory getParent() {
        return getContainingDirectory();
    }

    @Override
    @Nullable
    public PsiDirectory getContainingDirectory() {
        final VirtualFile parentFile = getViewProvider().getVirtualFile().getParent();
        if (parentFile == null) return null;
        return getManager().findDirectory(parentFile);
    }

    @Override
    @NotNull
    public PsiFile getContainingFile() {
        return this;
    }

    @Override
    public void delete() throws IncorrectOperationException {
        checkDelete();
        PsiFileImplUtil.doDelete(this);
    }

    @Override
    public void checkDelete() throws IncorrectOperationException {
        if (!getViewProvider().isEventSystemEnabled()) {
            throw new IncorrectOperationException();
        }
        CheckUtil.checkWritable(this);
    }

    @Override
    @NotNull
    public PsiFile getOriginalFile() {
        return myOriginalFile == null ? this : myOriginalFile;
    }

    public void setOriginalFile(@NotNull final PsiFile originalFile) {
        myOriginalFile = originalFile.getOriginalFile();
    }

    @Override
    @NotNull
    public PsiFile[] getPsiRoots() {
        final FileViewProvider viewProvider = getViewProvider();
        final Set<Language> languages = viewProvider.getLanguages();

        final PsiFile[] roots = new PsiFile[languages.size()];
        int i = 0;
        for (Language language : languages) {
            PsiFile psi = viewProvider.getPsi(language);
            if (psi == null) {
                LOG.error("PSI is null for " + language + "; in file: " + this);
            }
            roots[i++] = psi;
        }
        if (roots.length > 1) {
            Arrays.sort(roots, FILE_BY_LANGUAGE_ID);
        }
        return roots;
    }

    private static final Comparator<PsiFile> FILE_BY_LANGUAGE_ID = new Comparator<PsiFile>() {
        @Override
        public int compare(PsiFile o1, PsiFile o2) {
            return o1.getLanguage().getID().compareTo(o2.getLanguage().getID());
        }
    };

    @Override
    public boolean isPhysical() {
        return getViewProvider().isEventSystemEnabled();
    }

    @Override
    @NotNull
    public Language getLanguage() {
        return myElementType.getLanguage();
    }

    @Override
    @NotNull
    public FileViewProvider getViewProvider() {
        return myViewProvider;
    }

    @Override
    public PsiElement findElementAt(int offset) {
        return getViewProvider().findElementAt(offset);
    }

    @Override
    public PsiReference findReferenceAt(int offset) {
        return getViewProvider().findReferenceAt(offset);
    }

    @Override
    @NotNull
    public char[] textToCharArray() {
        return CharArrayUtil.fromSequence(getViewProvider().getContents());
    }


    @Override
    public PsiElement getContext() {
        return FileContextUtil.getFileContext(this);
    }

    @Override
    public void onContentReload() {
        subtreeChanged(); // important! otherwise cached information is not released
        //if (isContentsLoaded()) {
        //unloadContent();
        // }
    }

    @Override
    public PsiManager getManager() {
        return myManager;
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        return this;
    }

    @Override
    public PsiElement getOriginalElement() {
        return this;
    }

    @Override
    @NotNull
    public PsiElement[] getChildren() {
        return PsiASTUtil.getChildrenAsPsiElements(getNode(), null);
    }

    @Override
    public PsiElement getFirstChild() {
        return SharedImplUtil.getFirstChild(getNode());
    }

    @Override
    public PsiElement getLastChild() {
        return SharedImplUtil.getLastChild(getNode());
    }

    @Override
    public void acceptChildren(@NotNull PsiElementVisitor visitor) {
        SharedImplUtil.acceptChildren(visitor, getNode());
    }

    @Override
    public int getStartOffsetInParent() {
        return 0;
    }

    @Override
    public int getTextOffset() {
        return 0;
    }


    @Override
    public final PsiElement copy() {
        return clone();
    }

    @Override
    public PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        // TreeElement elementCopy = ChangeUtil.copyToElement(element);
        //  calcTreeElement().addInternal(elementCopy, elementCopy, null, null);
        // elementCopy = ChangeUtil.decodeInformation(elementCopy);
        //return SourceTreeToPsiMap.treeElementToPsi(elementCopy);
        throw new UnsupportedOperationException("todo!!!");
    }

    @Override
    public PsiElement addBefore(@NotNull PsiElement element, PsiElement anchor) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        // TreeElement elementCopy = ChangeUtil.copyToElement(element);
        // calcTreeElement().addInternal(elementCopy, elementCopy, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.TRUE);
        // elementCopy = ChangeUtil.decodeInformation(elementCopy);
        //  return SourceTreeToPsiMap.treeElementToPsi(elementCopy);
        throw new UnsupportedOperationException("todo!!!");

    }

    @Override
    public PsiElement addAfter(@NotNull PsiElement element, PsiElement anchor) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
//        TreeElement elementCopy = ChangeUtil.copyToElement(element);
//        calcTreeElement().addInternal(elementCopy, elementCopy, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.FALSE);
//        elementCopy = ChangeUtil.decodeInformation(elementCopy);
//        return SourceTreeToPsiMap.treeElementToPsi(elementCopy);
        throw new UnsupportedOperationException("todo!!!");

    }

    @Override
    public final void checkAdd(@NotNull PsiElement element) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
    }

    @Override
    public PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, first, last, null, null);
    }

    @Override
    public PsiElement addRangeBefore(@NotNull PsiElement first, @NotNull PsiElement last, PsiElement anchor)
            throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, first, last, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.TRUE);
    }

    @Override
    public PsiElement addRangeAfter(PsiElement first, PsiElement last, PsiElement anchor)
            throws IncorrectOperationException {
        return SharedImplUtil.addRange(this, first, last, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.FALSE);
    }

    @Override
    public void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
        CheckUtil.checkWritable(this);
        if (first == null) {
            LOG.assertTrue(last == null);
            return;
        }
        ASTNode firstElement = SourceTreeToPsiMap.psiElementToTree(first);
        ASTNode lastElement = SourceTreeToPsiMap.psiElementToTree(last);
        ASTNode node = getNode();
        LOG.assertTrue(firstElement.getTreeParent() == node);
        LOG.assertTrue(lastElement.getTreeParent() == node);
        CodeEditUtil.removeChildren(node, firstElement, lastElement);
    }

    @Override
    public PsiElement replace(@NotNull PsiElement newElement) throws IncorrectOperationException {
        throw new UnsupportedOperationException("TODO");

        // return SharedImplUtil.doReplace(this, getNode(), newElement);
    }

    @Override
    public PsiReference getReference() {
        return null;
    }

    @Override
    @NotNull
    public PsiReference[] getReferences() {
        return SharedPsiElementImplUtil.getReferences(this);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       PsiElement lastParent,
                                       @NotNull PsiElement place) {
        return true;
    }

    @Override
    @NotNull
    public GlobalSearchScope getResolveScope() {
        return ResolveScopeManager.getElementResolveScope(this);
    }

    @Override
    @NotNull
    public SearchScope getUseScope() {
        return ResolveScopeManager.getElementUseScope(this);
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Override
            public String getPresentableText() {
                return getName();
            }

            @Override
            public String getLocationString() {
                final PsiDirectory psiDirectory = getParent();
                if (psiDirectory != null) {
                    return psiDirectory.getVirtualFile().getPresentableUrl();
                }
                return null;
            }

            @Override
            public Icon getIcon(final boolean open) {
                return MyAbstractPsiFile.this.getIcon(0);
            }
        };
    }

    @Override
    public void navigate(boolean requestFocus) {
        assert canNavigate() : this;
        //noinspection ConstantConditions
        PsiNavigationSupport.getInstance().getDescriptor(this).navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return PsiNavigationSupport.getInstance().canNavigate(this);
    }

    @Override
    public boolean canNavigateToSource() {
        return canNavigate();
    }

    @Override
    @NotNull
    public Project getProject() {
        final PsiManager manager = getManager();
        if (manager == null) throw new PsiInvalidElementAccessException(this);

        return manager.getProject();
    }


    @Override
    public boolean isEquivalentTo(final PsiElement another) {
        return this == another;
    }

    @Override
    public String toString() {
        return myElementType.toString();
    }


}
