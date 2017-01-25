package exceptions.serverErrors;

import main.Protocol;

public class IllegalMethodUseException extends Exception {

	private static final long serialVersionUID = -6983782526186187852L;
	
	public IllegalMethodUseException(String msg) {
		super("ERROR " + Protocol.Error.ILLEGAL_METHOD_USE.code 
				+ ": Method is not allowed at this moment: " + msg);
	}
}
