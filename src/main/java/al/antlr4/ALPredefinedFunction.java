package main.java.al.antlr4;

import java.util.List;
import java.util.Map;

import al.antlr4.AluminumParser.ExpressionContext;

public abstract class ALPredefinedFunction extends ALFunction {

	public ALPredefinedFunction(String name, List<String> params) {
		super(name, null, null);
	}
	
	@Override
	public abstract ALObject invoke(List<ExpressionContext> args, Map<String, ALFunction> functions, ALScope scope);
	
}
