package org.antlr.intellij.plugin;

import com.intellij.testFramework.ParsingTestCase;

public class ANTLRv4ParsingTest extends ParsingTestCase {

    public ANTLRv4ParsingTest() {
        super("parsing", "g4", new ANTLRv4ParserDefinition());
    }

    public void testANTLRv4Lexer() throws Exception {
        doTest(false);
    }

    public void testANTLRv4Parser() throws Exception {
        doTest(true);
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