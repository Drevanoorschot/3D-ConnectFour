package exceptions.serverErrors;

public class IllegalMethodUseException extends Exception {

	private static final long serialVersionUID = -6983782526186187852L;
	
	public IllegalMethodUseException(String msg) {
		super("ERROR 111: Method is not allowed at this moment: " + msg);
	}
}
