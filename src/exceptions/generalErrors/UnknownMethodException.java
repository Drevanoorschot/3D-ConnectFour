package exceptions.generalErrors;

public class UnknownMethodException extends Exception {
	private static final long serialVersionUID = 5181870818387529592L;

	public UnknownMethodException() {
		super("ERROR 010: Unknown method");
	}

}
