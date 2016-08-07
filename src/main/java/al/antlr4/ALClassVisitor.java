package main.java.al.antlr4;

import java.util.HashMap;
import java.util.Map;

import al.antlr4.AluminumBaseVisitor;
import al.antlr4.AluminumParser.ClassStatementContext;
import main.java.al.antlr4.core.ALClass;
import main.java.al.antlr4.core.ALObject;

public class ALClassVisitor extends AluminumBaseVisitor<ALObject> {
	private Map<String, ALClass> classes;
	
	public ALClassVisitor() {
		classes = new HashMap<String, ALClass>();
	}

	public Map<String, ALClass> getClasses() {
		return classes;
	}
	
	@Override
	public ALObject visitClassStatement(ClassStatementContext ctx) {
		ALClass runtimeClass = new ALClass(ALClass.baseClass, ctx.block());
		String className = ctx.Identifier(0).getText();
		classes.put(className, runtimeClass);
		
		return ALObject.Void;
	}
	
}
