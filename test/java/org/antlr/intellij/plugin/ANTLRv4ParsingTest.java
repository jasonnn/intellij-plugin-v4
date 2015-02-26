package org.antlr.intellij.plugin;

import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.testFramework.ParsingTestCase;

public class ANTLRv4ParsingTest extends ParsingTestCase {

    public ANTLRv4ParsingTest() {
        super("parsing", "g4", new ANTLRv4ParserDefinition());
    }

    public void testANTLRv4Lexer() throws Exception {
        doTest(true);
    }

    public void testANTLRv4Parser() throws Exception {
        doTest(true);
    }

    public void testWhyDoesntItWork() throws Exception {
        String fileName = "ANTLRv4Lexer.g4";
        String lexText = loadFile(fileName);
        LightVirtualFile virtualFile = new LightVirtualFile(fileName, myLanguage, lexText);
        virtualFile.setCharset(CharsetToolkit.UTF8_CHARSET);

        PsiFile psiFile = createFile(virtualFile);

        assertNotNull(psiFile);

        ensureParsed(psiFile);

    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }

    @Override
    protected String getTestDataPath() {
        return ANTLRTestData.PATH;
    }
}