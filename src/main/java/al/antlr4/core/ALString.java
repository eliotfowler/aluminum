package main.java.al.antlr4.core;

import java.util.ArrayList;
import java.util.List;

import al.antlr4.AluminumParser.ExpressionContext;
import main.java.al.antlr4.ALEvalVisitor;

public class ALString extends ALObject {
	private String string;
	
	public ALString(String string) {
		this.string = string;
		
		// Set up predefined functions
		predefinedFunctions.put("uppercase", new UppercaseFunction());
		predefinedFunctions.put("lowercase", new LowercaseFunction());
		predefinedFunctions.put("append", new AppendFunction());
	}
	
	public String getString() {
		return string;
	}
	
	public ALString append(String stringToAppend) {
		return new ALString(string + stringToAppend);
	}
	
	@Override
	public String toString() {
		return string;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ALString)) {
			return false;
		}
		
		return ((ALString)obj).getString().equals(string);
	}
	
	private class UppercaseFunction extends ALPredefinedFunction {
		public UppercaseFunction() {
			super("uppercase", null);
		}

		@Override
		public ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope) {
			return new ALString(string.toUpperCase());
		}
	}
	
	private class LowercaseFunction extends ALPredefinedFunction {
		public LowercaseFunction() {
			super("lowercase", null);
		}

		@Override
		public ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope) {
			return new ALString(string.toLowerCase());
		}
	}
	
	private class AppendFunction extends ALPredefinedFunction {
		@SuppressWarnings("serial")
		public AppendFunction() {
			super("append", new ArrayList<String>() {{ add("stringToAppend"); }});
		}

		@Override
		public ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope) {
			ALString stringToAppend = (ALString)visitor.visit(args.get(0));
			return new ALString(string + stringToAppend.getString());
		}
	}
}
