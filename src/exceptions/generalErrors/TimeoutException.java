package exceptions.generalErrors;

import main.Protocol;

public class TimeoutException extends Exception {
	private static final long serialVersionUID = 1624213104492910164L;

	public TimeoutException() {
		super("ERROR " + Protocol.Error.TIMEOUT.code + ": A timeout occured");
	}
}
