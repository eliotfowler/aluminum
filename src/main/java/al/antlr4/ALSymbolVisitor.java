package main.java.al.antlr4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import al.antlr4.AluminumBaseVisitor;
import al.antlr4.AluminumParser.ClassStatementContext;
import al.antlr4.AluminumParser.FunctionDeclContext;
import main.java.al.antlr4.core.ALClass;
import main.java.al.antlr4.core.ALEnsureFunction;
import main.java.al.antlr4.core.ALFunction;
import main.java.al.antlr4.core.ALObject;
import main.java.al.antlr4.core.ALPrintFunction;

public class ALSymbolVisitor extends AluminumBaseVisitor<ALObject>{
	private Map<String, ALFunction> globalFunctions;
	private Map<String, ALClass> classes;
	
	public ALSymbolVisitor(Map<String, ALClass> classes) {
		this.classes = classes;
		globalFunctions = new HashMap<String, ALFunction>();
		addPredefinedFunctions();
	}
	
	public ALSymbolVisitor() {
		globalFunctions = new HashMap<String, ALFunction>();
		addPredefinedFunctions();
	}
	
	private void addPredefinedFunctions() {
		globalFunctions.put("print", new ALPrintFunction());
		globalFunctions.put("ensure", new ALEnsureFunction());
	}
	
	public Map<String, ALFunction> getGlobalFunctions() {
		return globalFunctions;
	}
	
	@Override
	public ALObject visitFunctionDecl(FunctionDeclContext ctx) {
		boolean hasParamList = ctx.parameters() != null && ctx.parameters().idList() != null;
		List<TerminalNode> params = hasParamList ? ctx.parameters().idList().Identifier() : new ArrayList<TerminalNode>();
		ParseTree block = ctx.block();
		String id = ctx.Identifier().getText();
		ALFunction newFunc = new ALFunction(id, params, block);
		
		// If we're inside a class, skip the method
		ParserRuleContext parent = ctx.getParent();
		
		while (parent != null) {
			if (parent instanceof ClassStatementContext) {
				String className = ((ClassStatementContext)parent).Identifier(0).getText();
				ALClass runtimeClass = classes.get(className);
				runtimeClass.defineInstanceFunction(id, newFunc);
				return ALObject.Void;
			}
			parent = parent.getParent();
		}
		
		globalFunctions.put(id, newFunc);
		return ALObject.Void;
	}
}
