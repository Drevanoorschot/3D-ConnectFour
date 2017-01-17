package exceptions;

public class FieldNotFreeException extends Exception {
	private static final long serialVersionUID = 2619161260773334771L;
	public FieldNotFreeException() {
		super("Field already taken!");
	}
	
}
