package org.antlr.intellij.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import com.intellij.testFramework.LightVirtualFile;
import org.antlr.intellij.plugin.psi.MyPsiUtils;

/**
 * Created by jason on 3/2/15.
 */
public class AntlrPsiTest extends ANTLRv4ParsingTest {

    PsiFile lexerFile;
    PsiFile parserFile;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        String fileName = "ANTLRv4Lexer.g4";
        String lexText = loadFile(fileName);
        LightVirtualFile virtualFile = new LightVirtualFile(fileName, myLanguage, lexText);
        virtualFile.setCharset(CharsetToolkit.UTF8_CHARSET);

        PsiFile psiFile = createFile(virtualFile);

        assertNotNull(psiFile);

        ensureParsed(psiFile);

        lexerFile = psiFile;


        String pfileName = "ANTLRv4Lexer.g4";
        String parserText = loadFile(fileName);
        LightVirtualFile pvirtualFile = new LightVirtualFile(pfileName, myLanguage, parserText);
        virtualFile.setCharset(CharsetToolkit.UTF8_CHARSET);

        PsiFile ppsiFile = createFile(virtualFile);

        assertNotNull(ppsiFile);

        ensureParsed(ppsiFile);
        parserFile = ppsiFile;
    }

    public void testFindTokenVocab() throws Exception {
        MyPsiUtils.findTokenVocabIfAny(parserFile);
    }

    public void testNextPrevNavigation() throws Exception {
        FileASTNode fileNode = parserFile.getNode();
        ASTNode firstChild = fileNode.getFirstChildNode();
        assertNotNull(fileNode);
        assertSame(fileNode,firstChild.getTreeParent());

        ASTNode secondChild = firstChild.getTreeNext();
        assertNotNull(secondChild);
        assertSame(fileNode,secondChild.getTreeParent());


        assertSame(firstChild,secondChild.getTreePrev());

        ASTNode lastChild = fileNode.getLastChildNode();

        System.out.println("lastChild = " + lastChild);
        System.out.println("firstChild = " + firstChild);

    }

    public void testRecursiveVisitor() throws Exception {
        parserFile.accept(new PsiRecursiveElementWalkingVisitor() {

        });

    }
}
