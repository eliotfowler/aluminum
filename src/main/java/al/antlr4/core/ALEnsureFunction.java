package main.java.al.antlr4.core;

import java.util.ArrayList;
import java.util.List;

import al.antlr4.AluminumParser.ExpressionContext;
import main.java.al.antlr4.ALEvalVisitor;

public class ALEnsureFunction extends ALPredefinedFunction {
	@SuppressWarnings("serial")
	private static ArrayList<String> params = new ArrayList<String>() {{ add("condition"); }};
	
	public ALEnsureFunction() {
		super("ensure", params);
	}

	@Override
	public ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope) {
		if (args.size() != params.size()) {
			throw new RuntimeException("Illegal function call");
		}
		
		scope = new ALScope(scope);
		try {
			ALBoolean value = (ALBoolean)visitor.visit(args.get(0));
			
			if (!value.getValue()) {
				throw new RuntimeException("Ensure failed - " + args.get(0).getText());
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("Illegal function call");
		}
		
		return ALObject.Void;
	}
	
	
}
