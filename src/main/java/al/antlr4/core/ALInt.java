package main.java.al.antlr4.core;

public class ALInt extends ALObject implements Comparable<ALInt> {
	private int number;
	
	public ALInt(int number) {
		this.number = number;
	}
	
	public int getInt() {
		return number;
	}
	
	public ALInt add(ALInt otherNumber) {
		return new ALInt(number + otherNumber.getInt());
	}
	
	public ALInt subtract(ALInt otherNumber) {
		return new ALInt(number - otherNumber.getInt());
	}
	
	public ALInt divide(ALInt otherNumber) {
		return new ALInt(number / otherNumber.getInt());
	}
	
	public ALInt multiply(ALInt otherNumber) {
		return new ALInt(number * otherNumber.getInt());
	}
	
	public ALInt pow(ALInt otherNumber) {
		return new ALInt((int)Math.pow(number, otherNumber.getInt()));
	}
	
	public ALInt mod(ALInt otherNumber) {
		return new ALInt(number % otherNumber.getInt());
	}

	@Override
	public int compareTo(ALInt o) {
		return Integer.compare(number, o.getInt());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ALInt)) {
			return false;
		}
		
		return ((ALInt)obj).getInt() == number;
	}
	
	@Override
	public String toString() {
		return "" + number;
	}
}
