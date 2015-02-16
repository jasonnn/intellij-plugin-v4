package org.antlr.intellij.plugin.paring;

import com.intellij.testFramework.ParsingTestCase;
import org.antlr.intellij.plugin.ANTLRTestData;
import org.antlr.intellij.plugin.ANTLRv4ParserDefinition;

/**
 * Created by jason on 2/13/15.
 */
public class ANTLRParsingTest extends ParsingTestCase {
	public ANTLRParsingTest() {
		super("parsing", "g4", new ANTLRv4ParserDefinition());
	}

	public void testANTLRv4Lexer() throws Exception {
		doTest(true);
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
