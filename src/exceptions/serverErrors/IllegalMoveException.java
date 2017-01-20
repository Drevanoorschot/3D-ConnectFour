package exceptions.serverErrors;

public class IllegalMoveException extends Exception {
	private static final long serialVersionUID = 1447949804929256749L;

	public IllegalMoveException() {
		super("ERROR 120: Illegal move");
	}
}
