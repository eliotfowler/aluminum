package main.java.al.antlr4;

import java.util.List;

import al.antlr4.AluminumParser.ExpressionContext;

public abstract class ALPredefinedFunction extends ALFunction {

	public ALPredefinedFunction(String name, List<String> params) {
		super(name, null, null);
	}
	
	@Override
	public abstract ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope);
	
}
