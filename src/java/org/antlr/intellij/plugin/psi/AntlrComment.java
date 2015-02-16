package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiComment;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.plugin.tokens.AntlrTokenTypes;

/**
 * Created by jason on 2/13/15.
 */
public class AntlrComment extends AntlrPsiElement implements PsiComment {
	public enum CommentKind {
		DOC,
		BLOCK,
		LINE;

		public static CommentKind forTokenType(IElementType type) {
			if (type == AntlrTokenTypes.DOC_COMMENT) return DOC;
			if (type == AntlrTokenTypes.BLOCK_COMMENT) return BLOCK;
			if (type == AntlrTokenTypes.LINE_COMMENT) return LINE;
			throw new IllegalArgumentException("expected a comment but got " + type.toString());
		}
	}

	public AntlrComment(ASTNode node) {
		super(node);
	}

	public CommentKind getKind() {
		return CommentKind.forTokenType(getTokenType());
	}

	@Override
	public IElementType getTokenType() {
		return getNode().getElementType();
	}
}
