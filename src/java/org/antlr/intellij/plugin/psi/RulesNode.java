package org.antlr.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class RulesNode extends AntlrPsiElement implements Iterable<RuleSpecNode>{
	public RulesNode(@NotNull ASTNode node) {
		super(node);
	}


	@NotNull
	@Override
	public Iterator<RuleSpecNode> iterator() {
		return null;
	}
}
