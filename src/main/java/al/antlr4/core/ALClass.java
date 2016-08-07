package main.java.al.antlr4.core;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;

public class ALClass {
	private Map<String, ALFunction> instanceFunctions;
	private Map<String, ALFunction> classFunctions;
	private ALClass superclass;
	private ParseTree block;
	
	public static ALClass baseClass = new ALClass();
	
	private ALClass() {
		
	}

	public ALClass(ALClass superclass, ParseTree block) {
		instanceFunctions = new HashMap<>();
		classFunctions = new HashMap<>();
		this.superclass = superclass;
		this.block = block;
	}

	public ALFunction lookupInstanceMethod(String name) {
		if (instanceFunctions.containsKey(name)) {
			return instanceFunctions.get(name);
		} else if (superclass != null) {
			return superclass.lookupInstanceMethod(name);
		} else {
			return null;
		}
	}
	
	public ALFunction lookupClassMethod(String name) {
		if (classFunctions.containsKey(name)) {
			return classFunctions.get(name);
		} else if (superclass != null) {
			return superclass.lookupClassMethod(name);
		} else {
			return null;
		}
	}

	public void defineInstanceFunction(String name, ALFunction function) {
		if (instanceFunctions.containsKey(name)) {
			throw new RuntimeException("Invalid redeclaration of function");
		}

		instanceFunctions.put(name, function);
	}
	
	public void defineClassFunction(String name, ALFunction function) {
		if (classFunctions.containsKey(name)) {
			throw new RuntimeException("Invalid redeclaration of function");
		}

		classFunctions.put(name, function);
	}

	public static ALObject create(ALClass runtimeClass, ALScope scope) {
		return new ALObject(runtimeClass, scope);
	}
}
