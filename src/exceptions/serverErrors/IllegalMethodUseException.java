package exceptions.serverErrors;

public class IllegalMethodUseException extends Exception {
	private static final long serialVersionUID = -6983782526186187852L;

	public IllegalMethodUseException() {
		super("ERROR 111: Not allowed to use this method right now");
	}
}
