package main.java.al.antlr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import al.antlr4.AluminumParser.ExpressionContext;

public class ALEnsureFunction extends ALPredefinedFunction {
	private static ArrayList<String> params = new ArrayList<String>() {{ add("condition"); }};
	
	public ALEnsureFunction() {
		super("ensure", params);
	}

	@Override
	public ALObject invoke(List<ExpressionContext> args, Map<String, ALFunction> functions, ALScope scope) {
		if (args.size() != params.size()) {
			throw new RuntimeException("Illegal function call");
		}
		
		scope = new ALScope(scope);
		ALEvalVisitor evalVisitor = new ALEvalVisitor(scope, functions);
		ALObject value = evalVisitor.visit(args.get(0));
		
		if (!value.isBoolean()) {
			throw new RuntimeException("Illegal function call");
		}
		
		if (!value.asBoolean()) {
			throw new RuntimeException("Ensure failed - " + args.get(0).getText());
		}
		
		return ALObject.Void;
	}
	
	
}
