package exceptions.generalErrors;

public class IllegalMethodSyntaxException extends Exception {
	private static final long serialVersionUID = -8337803104440944869L;

	public IllegalMethodSyntaxException() {
		super("ERROR 011: Illegal method syntax");
	}

}
