package main.java.al.antlr4;

public class ALObject implements Comparable<ALObject> {

	public static final ALObject Nil = new ALObject();
	public static final ALObject Void = new ALObject();
	
	private Object value;
	
	private ALObject() {
		value = new Object();
	}
	
	public ALObject(Object value) {
		if (value == null) {
			throw new RuntimeException("ALObject value is nil");
		} else if (value instanceof ALObject) {
			throw new RuntimeException("Value is ALObject");
		}
		
		this.value = value;
	}
	
	@Override
	public int compareTo(ALObject o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} 
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		ALObject rhs = (ALObject)obj;
		
		if (isNumber() && rhs.isNumber()) {
			return asNumber() == rhs.asNumber();
		}
		
		if (isBoolean() && rhs.isBoolean()) {
			return asBoolean() == rhs.asBoolean();
		}
		
		return value.equals(rhs.value);
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
	
	public boolean isBoolean() {
		return value instanceof Boolean;
	}
	
	public boolean isNumber() {
		return value instanceof Number;
	}
	
	public boolean asBoolean() {
		return (boolean)value;
	}
	
	public int asNumber() {
		return (int)value;
	}
	
	@Override
    public String toString() {
        return isNil() ? "nil" : isVoid() ? "VOID" : String.valueOf(value);
    }
}
