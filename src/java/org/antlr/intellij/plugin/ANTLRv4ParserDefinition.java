package org.antlr.intellij.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.plugin.adaptors.ANTLRv4GrammarParser;
import org.antlr.intellij.plugin.adaptors.ANTLRv4LexerAdaptor;
import org.antlr.intellij.plugin.parser.ANTLRv4Lexer;
import org.antlr.intellij.plugin.psi.AntlrPsiFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** The general interface between IDEA and ANTLR. */
public class ANTLRv4ParserDefinition implements ParserDefinition {
	public static final IFileElementType FILE =
			new IFileElementType("ANTLRv4_FILE", ANTLRv4Language.INSTANCE) {
				@Nullable
				@Override
				public ASTNode parseContents(ASTNode chameleon) {
					System.out.println("ANTLRv4ParserDefinition.FILE.parseContents");
					return super.parseContents(chameleon);
				}

				@Override
				protected ASTNode doParseContents(@NotNull ASTNode chameleon, @NotNull PsiElement psi) {
					System.out.println("ANTLRv4ParserDefinition.FILE.doParseContents");
					return super.doParseContents(chameleon, psi);
				}

				@Nullable
				@Override
				public ASTNode createNode(CharSequence text) {
					System.out.println("ANTLRv4ParserDefinition.FILE.createNode");
					return super.createNode(text);
				}
			};

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		System.out.println("ANTLRv4ParserDefinition.createLexer");
		ANTLRv4Lexer lexer = new ANTLRv4Lexer(null);
		return new ANTLRv4LexerAdaptor(ANTLRv4Language.INSTANCE, lexer);
	}

	@NotNull
	public PsiParser createParser(final Project project) {
		System.out.println("ANTLRv4ParserDefinition.createParser");
		//return new MyPsiParser();
		return new ANTLRv4GrammarParser();
	}

	@NotNull
	public TokenSet getWhitespaceTokens() {
		return ANTLRv4TokenTypes.WHITESPACES;
	}

	@NotNull
	public TokenSet getCommentTokens() {
		return ANTLRv4TokenTypes.COMMENTS;
	}

	@NotNull
	public TokenSet getStringLiteralElements() {
		return TokenSet.EMPTY;
	}

	@Override
	public IFileElementType getFileNodeType() {
		System.out.println("ANTLRv4ParserDefinition.getFileNodeType");
		return FILE;
		//return ANTLRv4FileElementType.INSTANCE;
	}

	@Override
	public PsiFile createFile(FileViewProvider viewProvider) {
		System.out.println("ANTLRv4ParserDefinition.createFile");
		//return new MyAntlrPsiFile(viewProvider);
		return new ANTLRv4FileRoot(viewProvider);
	}

	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
		return SpaceRequirements.MAY;
	}

	/** Convert from internal parse node (AST they call it) to final PSI node. This
	 *  converts only internal rule nodes apparently, not leaf nodes. Leaves
	 *  are just tokens I guess.
	 */
	@NotNull
	public PsiElement createElement(ASTNode node) {
//		if(node instanceof IFileElementType){
//			FileViewProvider viewProvider = PsiManager.getInstance(project).findViewProvider()
//
//		}
		return AntlrPsiFactory.createElement(node);
		//return ANTLRv4ASTFactory.createInternalParseTreeNode(node);
	}
}
