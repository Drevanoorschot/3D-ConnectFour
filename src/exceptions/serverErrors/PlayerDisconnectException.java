package exceptions.serverErrors;

public class PlayerDisconnectException extends Exception {
	private static final long serialVersionUID = -4828850935695786411L;
	public PlayerDisconnectException() {
		super("ERROR 110: Player Disconnected");
	}
}
