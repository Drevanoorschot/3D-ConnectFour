package exceptions.serverErrors;

public class UserAlreadyConnectedException extends Exception {
	private static final long serialVersionUID = -6959016274456849212L;

	public UserAlreadyConnectedException() {
		super("ERROR 190: User with this name already connected");
	}
}
