package main.java.al.antlr4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import al.antlr4.AluminumParser.ExpressionContext;

public class ALObject {

	public static final ALObject Nil = new ALObject();
	public static final ALObject Void = new ALObject();
	
	protected ALClass runtimeClass;
	protected Map<String, ALFunction> predefinedFunctions = new HashMap<>();
	
	protected ALObject() {}
	
	public ALObject(ALClass runtimeClass, ALScope scope) {
		this.runtimeClass = runtimeClass;
	}
	
	public void description() {
		System.out.println(this);
	}

	public boolean isNil() {
		return this == Nil;
	}
	
	public boolean isVoid() {
		return this == Void;
	}
	
	@Override
    public String toString() {
        return isNil() ? "nil" : isVoid() ? "VOID" : "<" + runtimeClass + ">";
    }
	
	public ALObject invokeFunction(String name, List<ExpressionContext> arguments, ALEvalVisitor visitor, ALScope scope) {
		if (runtimeClass != null) {
			ALFunction function = runtimeClass.lookupInstanceMethod(name);
			if (function != null) {
				return function.invoke(arguments, visitor, scope);
			}
		}
		
		if (predefinedFunctions.containsKey(name)) {
			ALFunction function = predefinedFunctions.get(name);
			return function.invoke(arguments, visitor, scope);
		}
		
		throw new RuntimeException("Function " + name + " doesn't exist for objects of " + this.getClass().getSimpleName());
	}
}
