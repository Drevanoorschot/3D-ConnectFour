package exceptions.generalErrors;

import main.Protocol;

public class IllegalMethodSyntaxException extends Exception {
	private static final long serialVersionUID = -8337803104440944869L;

	public IllegalMethodSyntaxException() {
		super("ERROR " + Protocol.Error.ILLEGAL_SYNTAX.code + ": Illegal method syntax");
	}

}
