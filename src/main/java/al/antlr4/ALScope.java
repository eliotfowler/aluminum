package main.java.al.antlr4;

import java.util.HashMap;
import java.util.Map;

public class ALScope {
	private ALScope parent;
	private Map<String, ALObject> variables;
	private boolean globalScope = false;
	
	public static ALScope RootScope = new ALScope();
	
	private ALScope() {
		this(null);
		globalScope = true;
	}
	
	public ALScope(ALScope parent) {
		this.parent = parent;
		variables = new HashMap<String, ALObject>();
	}
	
	public void assignArgument(String variable, ALObject value) {
		variables.put(variable, value);
	}
	
	public void assign(String variable, ALObject value) {
		if (resolve(variable) != null) {
			reassign(variable, value);
		} else {
			variables.put(variable, value);			
		}
	}
	
	public boolean isGlobalScope() {
		return globalScope;
	}
	
	public ALScope parent() {
		return parent;
	}
	
	private void reassign(String variable, ALObject value) {
		if (variables.containsKey(variable)) {
			variables.put(variable, value);
		} else {
			parent.assign(variable, value);
		}
	}
	
	public ALObject resolve(String variable) {
		ALObject value = variables.get(variable);
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
    	for(Map.Entry<String, ALObject> var: variables.entrySet()) {
    		sb.append(var.getKey() + "->" + var.getValue() +",");
    	}
    	return sb.toString();
    }
}
