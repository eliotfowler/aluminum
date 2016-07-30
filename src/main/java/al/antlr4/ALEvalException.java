package main.java.al.antlr4;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class ALEvalException extends RuntimeException {
	public ALEvalException(ParserRuleContext ctx) {
        this("Illegal expression: " + ctx.getText(), ctx);
    }
    
    public ALEvalException(String msg, ParserRuleContext ctx) {
        super(msg+" line:" + ctx.start.getLine());
    }
}
