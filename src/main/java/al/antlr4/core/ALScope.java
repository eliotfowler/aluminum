package main.java.al.antlr4.core;

import java.util.HashMap;
import java.util.Map;

public class ALScope {
	private ALScope parent;
	private Map<String, ALObject> localVariables;
	private boolean globalScope = false;
	
	public static ALScope RootScope = new ALScope();
	
	private ALScope() {
		this(null);
		globalScope = true;
		localVariables = new HashMap<>();
	}
	
	public ALScope(ALScope parent) {
		this.parent = parent;
		if (parent != null) {
			localVariables = parent.localVariables;
		} else {
			localVariables = new HashMap<>();
		}
	}
	
	public void assignArgument(String variable, ALObject value) {
		localVariables.put(variable, value);
	}
	
	public void assign(String variable, ALObject value) {
		if (resolve(variable) != null) {
			reassign(variable, value);
		} else {
			localVariables.put(variable, value);			
		}
	}
	
	public boolean isGlobalScope() {
		return globalScope;
	}
	
	public ALScope parent() {
		return parent;
	}
	
	private void reassign(String variable, ALObject value) {
		if (localVariables.containsKey(variable)) {
			localVariables.put(variable, value);
		} else {
			parent.assign(variable, value);
		}
	}
	
	public ALObject resolve(String variable) {
		ALObject value = localVariables.get(variable);
		if (value != null) {
			return value;
		} else if (!globalScope) {
			return parent.resolve(variable);
		}
		
		return null;
	}
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for(Map.Entry<String, ALObject> var: localVariables.entrySet()) {
    		sb.append(var.getKey() + "->" + var.getValue() +",");
    	}
    	return sb.toString();
    }
}
