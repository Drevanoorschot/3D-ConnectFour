package exceptions.serverErrors;

public class UserNotSupportingExtensionException extends Exception {
	private static final long serialVersionUID = -9048405437635252937L;

	public UserNotSupportingExtensionException() {
		super("ERROR 191: User does not support required extension");
	}
}
