package org.antlr.intellij.plugin.psi;

import com.intellij.psi.PsiElement;

/**
 * Created by jason on 2/13/15.
 */
public interface AntlrPsiApi {
	interface ANTLRv4GrammarSpec {

		Iterable<ANTLRv4ModeSpec> getModeSpecs();
		Iterable<ANTLRv4PrequelConstruct> getPrequelConstructs();
		ANTLRv4Id getId();
		ANTLRv4GrammarType getGrammarType();
		ANTLRv4Rules getRules();
		PsiElement getDOC_COMMENT();
		PsiElement getEOF();
		PsiElement getSEMI();

	}
	interface ANTLRv4GrammarType {

		PsiElement getPARSER();
		PsiElement getLEXER();
		PsiElement getGRAMMAR();

	}
	interface ANTLRv4PrequelConstruct {

		ANTLRv4TokensSpec getTokensSpec();
		ANTLRv4Action getAction();
		ANTLRv4DelegateGrammars getDelegateGrammars();
		ANTLRv4OptionsSpec getOptionsSpec();

	}
	interface ANTLRv4OptionsSpec {

		Iterable<ANTLRv4Option> getOptions();
		Iterable<PsiElement> getSEMIs();
		PsiElement getRBRACE();
		PsiElement getOPTIONS();

	}
	interface ANTLRv4Option {

		ANTLRv4Id getId();
		ANTLRv4OptionValue getOptionValue();
		PsiElement getASSIGN();

	}
	interface ANTLRv4OptionValue {

		Iterable<ANTLRv4Id> getIds();
		ANTLRv4Id getId();
		Iterable<PsiElement> getDOTs();
		PsiElement getINT();
		PsiElement getSTRING_LITERAL();
		PsiElement getACTION();

	}
	interface ANTLRv4DelegateGrammars {

		Iterable<ANTLRv4DelegateGrammar> getDelegateGrammars();
		ANTLRv4DelegateGrammar getDelegateGrammar();
		Iterable<PsiElement> getCOMMAs();
		PsiElement getIMPORT();
		PsiElement getSEMI();

	}
	interface ANTLRv4DelegateGrammar {

		ANTLRv4Id getId();
		PsiElement getASSIGN();

	}
	interface ANTLRv4TokensSpec {

		Iterable<ANTLRv4Id> getIds();
		ANTLRv4Id getId();
		Iterable<PsiElement> getCOMMAs();
		PsiElement getTOKENS();
		PsiElement getRBRACE();
		PsiElement getCOMMA();

	}
	interface ANTLRv4Action {

		ANTLRv4Id getId();
		ANTLRv4ActionScopeName getActionScopeName();
		PsiElement getAT();
		PsiElement getCOLONCOLON();
		PsiElement getACTION();

	}
	interface ANTLRv4ActionScopeName {

		ANTLRv4Id getId();
		PsiElement getPARSER();
		PsiElement getLEXER();

	}
	interface ANTLRv4ModeSpec {

		Iterable<ANTLRv4LexerRule> getLexerRules();
		ANTLRv4Id getId();
		PsiElement getSEMI();
		PsiElement getMODE();

	}
	interface ANTLRv4Rules {

		Iterable<ANTLRv4RuleSpec> getRuleSpecs();

	}
	interface ANTLRv4RuleSpec {

		ANTLRv4ParserRuleSpec getParserRuleSpec();
		ANTLRv4LexerRule getLexerRule();

	}
	interface ANTLRv4ParserRuleSpec {

		Iterable<ANTLRv4RulePrequel> getRulePrequels();
		ANTLRv4ExceptionGroup getExceptionGroup();
		ANTLRv4RuleModifiers getRuleModifiers();
		ANTLRv4RuleReturns getRuleReturns();
		ANTLRv4RuleBlock getRuleBlock();
		ANTLRv4ThrowsSpec getThrowsSpec();
		ANTLRv4LocalsSpec getLocalsSpec();
		PsiElement getCOLON();
		PsiElement getDOC_COMMENT();
		PsiElement getSEMI();
		PsiElement getRULE_REF();
		PsiElement getARG_ACTION();

	}
	interface ANTLRv4ExceptionGroup {

		Iterable<ANTLRv4ExceptionHandler> getExceptionHandlers();
		ANTLRv4FinallyClause getFinallyClause();

	}
	interface ANTLRv4ExceptionHandler {

		PsiElement getCATCH();
		PsiElement getACTION();
		PsiElement getARG_ACTION();

	}
	interface ANTLRv4FinallyClause {

		PsiElement getFINALLY();
		PsiElement getACTION();

	}
	interface ANTLRv4RulePrequel {

		ANTLRv4OptionsSpec getOptionsSpec();
		ANTLRv4RuleAction getRuleAction();

	}
	interface ANTLRv4RuleReturns {

		PsiElement getRETURNS();
		PsiElement getARG_ACTION();

	}
	interface ANTLRv4ThrowsSpec {

		Iterable<ANTLRv4Id> getIds();
		ANTLRv4Id getId();
		Iterable<PsiElement> getCOMMAs();
		PsiElement getTHROWS();

	}
	interface ANTLRv4LocalsSpec {

		PsiElement getLOCALS();
		PsiElement getARG_ACTION();

	}
	interface ANTLRv4RuleAction {

		ANTLRv4Id getId();
		PsiElement getAT();
		PsiElement getACTION();

	}
	interface ANTLRv4RuleModifiers {

		Iterable<ANTLRv4RuleModifier> getRuleModifiers();

	}
	interface ANTLRv4RuleModifier {

		PsiElement getPROTECTED();
		PsiElement getPRIVATE();
		PsiElement getPUBLIC();
		PsiElement getFRAGMENT();

	}
	interface ANTLRv4RuleBlock {

		ANTLRv4RuleAltList getRuleAltList();

	}
	interface ANTLRv4RuleAltList {

		Iterable<ANTLRv4LabeledAlt> getLabeledAlts();
		ANTLRv4LabeledAlt getLabeledAlt();
		Iterable<PsiElement> getORs();

	}
	interface ANTLRv4LabeledAlt {

		ANTLRv4Id getId();
		ANTLRv4Alternative getAlternative();
		PsiElement getPOUND();

	}
	interface ANTLRv4LexerRule {

		ANTLRv4LexerRuleBlock getLexerRuleBlock();
		PsiElement getCOLON();
		PsiElement getDOC_COMMENT();
		PsiElement getSEMI();
		PsiElement getFRAGMENT();
		PsiElement getTOKEN_REF();

	}
	interface ANTLRv4LexerRuleBlock {

		ANTLRv4LexerAltList getLexerAltList();

	}
	interface ANTLRv4LexerAltList {

		Iterable<ANTLRv4LexerAlt> getLexerAlts();
		ANTLRv4LexerAlt getLexerAlt();
		Iterable<PsiElement> getORs();

	}
	interface ANTLRv4LexerAlt {

		ANTLRv4LexerElements getLexerElements();
		ANTLRv4LexerCommands getLexerCommands();

	}
	interface ANTLRv4LexerElements {

		Iterable<ANTLRv4LexerElement> getLexerElements();

	}
	interface ANTLRv4LexerElement {

		ANTLRv4EbnfSuffix getEbnfSuffix();
		ANTLRv4LexerBlock getLexerBlock();
		ANTLRv4LabeledLexerElement getLabeledLexerElement();
		ANTLRv4LexerAtom getLexerAtom();
		PsiElement getQUESTION();
		PsiElement getACTION();

	}
	interface ANTLRv4LabeledLexerElement {

		ANTLRv4Id getId();
		ANTLRv4Block getBlock();
		ANTLRv4LexerAtom getLexerAtom();
		PsiElement getPLUS_ASSIGN();
		PsiElement getASSIGN();

	}
	interface ANTLRv4LexerBlock {

		ANTLRv4LexerAltList getLexerAltList();
		PsiElement getRPAREN();
		PsiElement getLPAREN();

	}
	interface ANTLRv4LexerCommands {

		Iterable<ANTLRv4LexerCommand> getLexerCommands();
		ANTLRv4LexerCommand getLexerCommand();
		Iterable<PsiElement> getCOMMAs();
		PsiElement getRARROW();

	}
	interface ANTLRv4LexerCommand {

		ANTLRv4LexerCommandExpr getLexerCommandExpr();
		ANTLRv4LexerCommandName getLexerCommandName();
		PsiElement getRPAREN();
		PsiElement getLPAREN();

	}
	interface ANTLRv4LexerCommandName {

		ANTLRv4Id getId();
		PsiElement getMODE();

	}
	interface ANTLRv4LexerCommandExpr {

		ANTLRv4Id getId();
		PsiElement getINT();

	}
	interface ANTLRv4AltList {

		Iterable<ANTLRv4Alternative> getAlternatives();
		ANTLRv4Alternative getAlternative();
		Iterable<PsiElement> getORs();

	}
	interface ANTLRv4Alternative {

		Iterable<ANTLRv4Element> getElements();
		ANTLRv4ElementOptions getElementOptions();

	}
	interface ANTLRv4Element {

		ANTLRv4Atom getAtom();
		ANTLRv4EbnfSuffix getEbnfSuffix();
		ANTLRv4Ebnf getEbnf();
		ANTLRv4LabeledElement getLabeledElement();
		PsiElement getQUESTION();
		PsiElement getACTION();

	}
	interface ANTLRv4LabeledElement {

		ANTLRv4Id getId();
		ANTLRv4Atom getAtom();
		ANTLRv4Block getBlock();
		PsiElement getPLUS_ASSIGN();
		PsiElement getASSIGN();

	}
	interface ANTLRv4Ebnf {

		ANTLRv4Block getBlock();
		ANTLRv4BlockSuffix getBlockSuffix();

	}
	interface ANTLRv4BlockSuffix {

		ANTLRv4EbnfSuffix getEbnfSuffix();

	}
	interface ANTLRv4EbnfSuffix {

		PsiElement getPLUS();
		PsiElement getSTAR();
		PsiElement getQUESTION();

	}
	interface ANTLRv4LexerAtom {

		ANTLRv4Terminal getTerminal();
		ANTLRv4Range getRange();
		ANTLRv4ElementOptions getElementOptions();
		ANTLRv4NotSet getNotSet();
		PsiElement getLEXER_CHAR_SET();
		PsiElement getDOT();
		PsiElement getRULE_REF();

	}
	interface ANTLRv4Atom {

		ANTLRv4Terminal getTerminal();
		ANTLRv4Range getRange();
		ANTLRv4ElementOptions getElementOptions();
		ANTLRv4NotSet getNotSet();
		ANTLRv4Ruleref getRuleref();
		PsiElement getDOT();

	}
	interface ANTLRv4NotSet {

		ANTLRv4SetElement getSetElement();
		ANTLRv4BlockSet getBlockSet();
		PsiElement getNOT();

	}
	interface ANTLRv4BlockSet {

		Iterable<ANTLRv4SetElement> getSetElements();
		ANTLRv4SetElement getSetElement();
		Iterable<PsiElement> getORs();
		PsiElement getRPAREN();
		PsiElement getLPAREN();

	}
	interface ANTLRv4SetElement {

		ANTLRv4Range getRange();
		ANTLRv4ElementOptions getElementOptions();
		PsiElement getLEXER_CHAR_SET();
		PsiElement getSTRING_LITERAL();
		PsiElement getTOKEN_REF();

	}
	interface ANTLRv4Block {

		Iterable<ANTLRv4RuleAction> getRuleActions();
		ANTLRv4OptionsSpec getOptionsSpec();
		ANTLRv4AltList getAltList();
		PsiElement getCOLON();
		PsiElement getRPAREN();
		PsiElement getLPAREN();

	}
	interface ANTLRv4Ruleref {

		ANTLRv4ElementOptions getElementOptions();
		PsiElement getRULE_REF();
		PsiElement getARG_ACTION();

	}
	interface ANTLRv4Range {

		PsiElement getRANGE();
		PsiElement getSTRING_LITERAL();

	}
	interface ANTLRv4Terminal {

		ANTLRv4ElementOptions getElementOptions();
		PsiElement getSTRING_LITERAL();
		PsiElement getTOKEN_REF();

	}
	interface ANTLRv4ElementOptions {

		Iterable<ANTLRv4ElementOption> getElementOptions();
		ANTLRv4ElementOption getElementOption();
		Iterable<PsiElement> getCOMMAs();
		PsiElement getGT();
		PsiElement getLT();

	}
	interface ANTLRv4ElementOption {

		ANTLRv4Id getId();
		PsiElement getSTRING_LITERAL();
		PsiElement getASSIGN();

	}
	interface ANTLRv4Id {

		PsiElement getRULE_REF();
		PsiElement getTOKEN_REF();

	}
}
