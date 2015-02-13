//import org.antlr.v4.tool.Grammar
//import org.antlr.v4.tool.LexerGrammar

/**
 * Created by jason on 2/12/15.
 */
def rootLoader = this.class.classLoader.rootLoader
def antlrJar = new File('./lib/antlr-4.5-complete.jar').absoluteFile
rootLoader.addURL(antlrJar.toURL())


new GenTypes().run()

class GenTypes implements Runnable {
    File generatedRulesFile, generatedTokensFile

    boolean doGenerate = true

    def /*Grammar*/ grammar

    GenTypes() {
        def genSrcDir = 'gen/org/antlr/intellij/plugin/tokens' as File
        if (!genSrcDir.exists()) genSrcDir.mkdirs()

        generatedRulesFile = new File(genSrcDir, 'AntlrRuleTypes.java')

        generatedTokensFile = new File(genSrcDir, 'AntlrTokenTypes.java')

        def grammarsPath = 'src/grammars/org/antlr/intellij/plugin/parser/' as File

        def lexerFile = new File(grammarsPath, 'ANTLRv4Lexer.g4')
        def parserFile = new File(grammarsPath, 'ANTLRv4Parser.g4')

        if (generatedRulesFile.exists()
                && generatedRulesFile.lastModified() > parserFile.lastModified()
                && generatedTokensFile.exists()
                && generatedTokensFile.lastModified() > lexerFile.lastModified()) {
            doGenerate = false
        }

        if (doGenerate) {
            def lgCls = 'org.antlr.v4.tool.LexerGrammar' as Class
            def gCls = 'org.antlr.v4.tool.Grammar' as Class
            def lexerGrammar = lgCls.newInstance(lexerFile.text) //new LexerGrammar(lexerFile.text)
            grammar = gCls.newInstance(parserFile.text, lexerGrammar) //new Grammar(parserFile.text, lexerGrammar)
        }


    }

    static def ruleElementType(String name) {
        "public static final RuleElementType $name = ANTLRv4TokenTypes.getRuleElementType(ANTLRv4Parser.RULE_$name);"

    }

    static def tokenElementType(String name) {
        "public static final TokenElementType $name = ANTLRv4TokenTypes.getTokenElementType(ANTLRv4Lexer.$name);"
    }

    @Override
    void run() {
        if (!doGenerate) {
            println "no need to generate"
            return
        }

        generatedTokensFile.withPrintWriter('UTF-8') { pw ->
            pw.println 'package org.antlr.intellij.plugin.tokens; \n'

            pw.println 'import org.antlr.intellij.adaptor.lexer.TokenElementType;'
            pw.println 'import org.antlr.intellij.plugin.ANTLRv4TokenTypes;'
            pw.println 'import org.antlr.intellij.plugin.parser.ANTLRv4Lexer;'

            pw.println 'public class AntlrTokenTypes { \n'

            grammar.tokenNames.each { name -> if (name != '<INVALID>') pw.println(tokenElementType(name)) }

            pw.println '\n}'

        }

        generatedRulesFile.withPrintWriter('UTF-8') { pw ->
            pw.println 'package org.antlr.intellij.plugin.tokens; \n'

            pw.println 'import org.antlr.intellij.adaptor.lexer.RuleElementType;'
            pw.println 'import org.antlr.intellij.plugin.ANTLRv4TokenTypes;'
            pw.println 'import org.antlr.intellij.plugin.parser.ANTLRv4Parser;'

            pw.println 'public class AntlrRuleTypes { \n'

            grammar.ruleNames.each { name -> pw.println ruleElementType(name) }

            pw.println '\n}'


        }

    }
}





