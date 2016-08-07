package main.java.al.antlr4.core;

import main.java.al.antlr4.ALObject;

public class ALBoolean extends ALObject {
	private boolean value;
	
	public ALBoolean(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public ALBoolean and(ALBoolean otherBoolean) {
		return new ALBoolean(value && otherBoolean.getValue());
	}
	
	public ALBoolean or(ALBoolean otherBoolean) {
		return new ALBoolean(value || otherBoolean.getValue());
	}
	
	@Override
	public String toString() {
		return value ? "true" : "false";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ALBoolean)) {
			return false;
		}
		
		return ((ALBoolean)obj).getValue() == value;
	}
}
