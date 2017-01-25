package exceptions.serverErrors;

import main.Protocol;

public class IllegalMoveException extends Exception {
	private static final long serialVersionUID = 1447949804929256749L;

	public IllegalMoveException() {
		super("ERROR " + Protocol.Error.ILLEGAL_MOVE.code + ": Illegal move");
	}
}
