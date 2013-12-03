package org.antlr.intellij.plugin;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.plugin.adaptors.ANTLRUtils;
import org.antlr.intellij.plugin.adaptors.LexerAdaptor;
import org.antlr.intellij.plugin.parser.ANTLRv4Lexer;
import org.antlr.intellij.plugin.parser.ANTLRv4TokenTypes;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class ANTLRv4SyntaxHighlighter extends SyntaxHighlighterBase {
	public static final TextAttributes RULE_ATTRIBUTES = CodeInsightColors.INSTANCE_FIELD_ATTRIBUTES.getDefaultAttributes().clone();
	public static final TextAttributes TOKEN_ATTRIBUTES = CodeInsightColors.INSTANCE_FIELD_ATTRIBUTES.getDefaultAttributes().clone();
//	public static final TextAttributes KEYWORD_ATTRIBUTES = SyntaxHighlighterColors.KEYWORD.getDefaultAttributes().clone();
	static{
		RULE_ATTRIBUTES.setForegroundColor(new Color(102,100,191));
//		TOKEN_ATTRIBUTES.setForegroundColor(new Color(98,60,150));
//	TOKEN_ATTRIBUTES.setForegroundColor(new Color(80,72,117));
	TOKEN_ATTRIBUTES.setForegroundColor(new Color(130,72,146));
//		KEYWORD_ATTRIBUTES.setFontType(Font.ITALIC);
	}

//	public static final TextAttributesKey KEYWORD = createTextAttributesKey("KEYWORD", KEYWORD_ATTRIBUTES);
	public static final TextAttributesKey RULENAME = createTextAttributesKey("RULENAME", RULE_ATTRIBUTES);
	public static final TextAttributesKey TOKENNAME = createTextAttributesKey("TOKENNAME", TOKEN_ATTRIBUTES);
	public static final TextAttributesKey STRING =
		createTextAttributesKey("STRING", SyntaxHighlighterColors.STRING);
	public static final TextAttributesKey LINE_COMMENT =
		createTextAttributesKey("LINE_COMMENT", SyntaxHighlighterColors.LINE_COMMENT);
	public static final TextAttributesKey JAVADOC_COMMENT =
		createTextAttributesKey("JAVADOC_COMMENT", SyntaxHighlighterColors.JAVA_BLOCK_COMMENT);
	public static final TextAttributesKey BLOCK_COMMENT =
		createTextAttributesKey("BLOCK_COMMENT", SyntaxHighlighterColors.JAVA_BLOCK_COMMENT);

	static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("SIMPLE_BAD_CHARACTER",
																		   new TextAttributes(Color.RED, null, null, null, Font.BOLD));

	private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
	private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
	private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT, JAVADOC_COMMENT, BLOCK_COMMENT};
	private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		final ANTLRv4Lexer lexer = new ANTLRv4Lexer(null);
		LexerATNSimulator sim =
			ANTLRUtils.getLexerATNSimulator(lexer, ANTLRv4Lexer._ATN, lexer.getInterpreter().decisionToDFA,
											lexer.getInterpreter().getSharedContextCache());
		lexer.setInterpreter(sim);
		return new LexerAdaptor(lexer);
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
//		if ( ANTLRv4TokenTypeAdaptor.KEYWORDS.contains(tokenType) ){
//			return new TextAttributesKey[]{KEYWORD};
//		}
//		else

		if ( tokenType == ANTLRv4TokenTypes.TOKEN_REF ) {
			return new TextAttributesKey[]{TOKENNAME};
		}
		else if ( tokenType == ANTLRv4TokenTypes.RULE_REF ) {
			return new TextAttributesKey[]{RULENAME};
		}
		else if (tokenType == ANTLRv4TokenTypes.STRING_LITERAL) {
			return STRING_KEYS;
		}
		else if (tokenType == ANTLRv4TokenTypes.BLOCK_COMMENT) {
			return COMMENT_KEYS;
		}
		else if (tokenType == ANTLRv4TokenTypes.DOC_COMMENT) {
			return COMMENT_KEYS;
		}
		else if (tokenType == ANTLRv4TokenTypes.LINE_COMMENT) {
			return COMMENT_KEYS;
		}
		else if (tokenType == ANTLRv4TokenTypes.BAD_TOKEN ) {
			return BAD_CHAR_KEYS;
		}
		else {
			return EMPTY_KEYS;
		}
	}
}