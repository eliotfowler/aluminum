package main.java.al.antlr4.core;

import java.util.List;

import al.antlr4.AluminumParser.ExpressionContext;
import main.java.al.antlr4.ALEvalVisitor;

public abstract class ALPredefinedFunction extends ALFunction {

	public ALPredefinedFunction(String name, List<String> params) {
		super(name, null, null);
	}
	
	@Override
	public abstract ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope);
	
}
