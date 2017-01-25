package exceptions.serverErrors;

import main.Protocol;

public class UserNotSupportingExtensionException extends Exception {
	private static final long serialVersionUID = -9048405437635252937L;

	public UserNotSupportingExtensionException() {
		super("ERROR " + Protocol.Error.USER_NOSUPPORT_EXTENSION.code 
				+ ": User does not support required extension");
	}
}
