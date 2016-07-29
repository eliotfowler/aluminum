package main.java.al.antlr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import al.antlr4.AluminumParser.ExpressionContext;

public class ALPrintFunction extends ALPredefinedFunction {

	private static ArrayList<String> params = new ArrayList<String>() {{ add("toPrint"); }};
	
	public ALPrintFunction() {
		super("print", params);
	}

	@Override
	public ALObject invoke(List<ExpressionContext> args, Map<String, ALFunction> functions, ALScope scope) {
		if (args.size() != params.size()) {
			throw new RuntimeException("Illegal function call");
		}
		
		scope = new ALScope(scope);
		ALEvalVisitor evalVisitor = new ALEvalVisitor(scope, functions);
		ALObject value = evalVisitor.visit(args.get(0));
		System.out.println(value.toString());
		
		return ALObject.Void;
	}

}
