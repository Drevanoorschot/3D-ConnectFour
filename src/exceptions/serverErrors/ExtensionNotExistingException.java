package exceptions.serverErrors;

public class ExtensionNotExistingException extends Exception {
	private static final long serialVersionUID = -5723743919631313931L;

	public ExtensionNotExistingException() {
		super("ERROR 020: Extension does not exist");
	}

}
