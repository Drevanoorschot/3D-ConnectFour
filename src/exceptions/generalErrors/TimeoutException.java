package exceptions.generalErrors;

public class TimeoutException extends Exception {
	private static final long serialVersionUID = 1624213104492910164L;

	public TimeoutException() {
		super("ERROR 012: A timeout occured");
	}
}
