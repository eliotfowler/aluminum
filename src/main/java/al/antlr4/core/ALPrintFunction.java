package main.java.al.antlr4.core;

import java.util.ArrayList;
import java.util.List;

import al.antlr4.AluminumParser.ExpressionContext;
import main.java.al.antlr4.ALEvalVisitor;

public class ALPrintFunction extends ALPredefinedFunction {

	@SuppressWarnings("serial")
	private static ArrayList<String> params = new ArrayList<String>() {{ add("toPrint"); }};
	
	public ALPrintFunction() {
		super("print", params);
	}

	@Override
	public ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope) {
		if (args.size() != params.size()) {
			throw new RuntimeException("Illegal function call");
		}
		
		scope = new ALScope(scope);
		ALObject value = visitor.visit(args.get(0));
		System.out.println(value.toString());
		
		return ALObject.Void;
	}

}
