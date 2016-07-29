package main.java.al.antlr4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import al.antlr4.AluminumBaseVisitor;
import al.antlr4.AluminumParser.FunctionDeclContext;

public class ALSymbolVisitor extends AluminumBaseVisitor<ALObject>{
	private Map<String, ALFunction> functions;
	
	public ALSymbolVisitor(Map<String, ALFunction> functions) {
		this.functions = functions;
		addPredefinedFunctions();
	}
	
	public ALSymbolVisitor() {
		functions = new HashMap<String, ALFunction>();
		addPredefinedFunctions();
	}
	
	private void addPredefinedFunctions() {
		functions.put("print", new ALPrintFunction());
		functions.put("ensure", new ALEnsureFunction());
	}
	
	public Map<String, ALFunction> getFunctions() {
		return functions;
	}
	
	@Override
	public ALObject visitFunctionDecl(FunctionDeclContext ctx) {
		List<TerminalNode> params = ctx.parameters().idList() != null ? ctx.parameters().idList().Identifier() : new ArrayList<TerminalNode>();
		ParseTree block = ctx.block();
		String id = ctx.Identifier().getText();
		functions.put(id, new ALFunction(id, params, block));
		return ALObject.Void;
	}
}
