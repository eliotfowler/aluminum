package main.java.al.antlr4;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import al.antlr4.AluminumParser.ExpressionContext;

public class ALFunction {
	private String name;
	private List<TerminalNode> params;
	private ParseTree block;
	
	public ALFunction(String name, List<TerminalNode> params, ParseTree block) {
		this.name = name;
		this.params = params;
		this.block = block;
	}
	
	public ALObject invoke(List<ExpressionContext> args, ALEvalVisitor visitor, ALScope scope) {
		if (args.size() != params.size()) {
			throw new RuntimeException("Illegal function call");
		}
		
		scope = new ALScope(scope);
		
		// First assign all the parameters to the function
		for (int i = 0; i < params.size(); i++) {
			ALObject value = visitor.visit(args.get(i));
			scope.assignArgument(params.get(i).getText(), value);
		}
		
		ALObject retVal = ALObject.Void;
		try {
			ALEvalVisitor newVisitor = new ALEvalVisitor(visitor); 
			newVisitor.visit(block);
		} catch (ALReturnValue returnValue) {
			retVal = returnValue.value;
		}
		
		return retVal;
	}
	
	@Override
	public String toString() {
		String paramsString = "";
		if (params != null) {
			paramsString = params.toString(); 
		}
		
		return "ALFunction::" + name + "(" + paramsString + ")";
	}
}
