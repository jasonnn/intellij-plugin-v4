package org.antlr.intellij.plugin.formatter;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.formatter.FormatterTestCase;
import com.intellij.testFramework.IdeaTestCase;
import com.intellij.testFramework.TestLoggerFactory;
import org.antlr.intellij.plugin.ANTLRTestData;
/*
note: right now these tests just test against changes in formatting results, not correctness
 */
public class ANTLRv4FormattingTest extends FormatterTestCase {
    static {
        Logger.setFactory(TestLoggerFactory.class);
    }

    public void testBasics_Rules() throws Exception {
        doTest();
    }

    public void testBasics_Spacing() throws Exception {
        doTest();
    }

    public void testFormattingExamples() throws Exception {
        doTest();
    }

    @Override
    protected void setUp() throws Exception {
       IdeaTestCase.initPlatformPrefix();
        super.setUp();
    }

    @Override
    protected String getTestName(boolean ignored) {
        return super.getTestName(false);
    }

    @Override
    protected String getBasePath() {
        return "formatting";
    }

    @Override
    protected String getTestDataPath() {
        return ANTLRTestData.PATH;
    }

    @Override
    protected String getFileExtension() {
        return "g4";
    }
}