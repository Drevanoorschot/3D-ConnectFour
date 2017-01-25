package exceptions.serverErrors;

import main.Protocol;

public class UserAlreadyConnectedException extends Exception {
	private static final long serialVersionUID = -6959016274456849212L;

	public UserAlreadyConnectedException() {
		super("ERROR " + Protocol.Error.USER_ALREADY_CONNECTED.code 
				+ ": User with this name already connected");
	}
}
