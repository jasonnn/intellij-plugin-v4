package scratch;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.*;

/**
 * Created by jason on 2/26/15.
 */
public class InterceptingParserRuleContext extends ParserRuleContext {
    interface TreeFactory<N extends RuleNode> {
        TerminalNode createTerminalNode(N parent, Token token);

        ErrorNode createErrorNode(N parent, Token badToken);

        N interceptAddChild(N parent, N child);

    }


    enum DefaultTreeFactory implements TreeFactory<RuleContext> {
        INSTANCE;

        @Override
        public TerminalNode createTerminalNode(RuleContext parent, Token token) {
            TerminalNodeImpl result = new TerminalNodeImpl(token);
            result.parent = parent;
            return result;
        }

        @Override
        public ErrorNode createErrorNode(RuleContext parent, Token badToken) {
            ErrorNodeImpl result = new ErrorNodeImpl(badToken);
            result.parent = parent;
            return result;
        }

        @Override
        public RuleContext interceptAddChild(RuleContext parent, RuleContext child) {
            return child;
        }
    }

    final TreeFactory<RuleContext> treeFactory = DefaultTreeFactory.INSTANCE;

    public InterceptingParserRuleContext() {
    }

    public InterceptingParserRuleContext(ParserRuleContext parent, int invokingStateNumber) {
        super(parent, invokingStateNumber);
    }

    public TerminalNode addChild(Token matchedToken) {
        TerminalNode terminalNode = treeFactory.createTerminalNode(this, matchedToken);
        addChild(terminalNode);
        return terminalNode;
    }

    public ErrorNode addErrorNode(Token badToken) {
        ErrorNode errorNode = treeFactory.createErrorNode(this, badToken);
        addChild(errorNode);
        return errorNode;
    }

    void checkParent(Tree tree) {
        assert tree.getParent() == this;
    }


    public TerminalNode addChild(TerminalNode t) {
        checkParent(t);
        return super.addChild(t);
    }

    public RuleContext addChild(RuleContext ruleInvocation) {
        RuleContext child = treeFactory.interceptAddChild(this, ruleInvocation);
        checkParent(child);
        return super.addChild(child);
    }
}
