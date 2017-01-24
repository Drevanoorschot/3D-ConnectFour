package exceptions.serverErrors;

import main.Protocol;

public class PlayerDisconnectException extends Exception {
	private static final long serialVersionUID = -4828850935695786411L;
	public PlayerDisconnectException() {
		super("ERROR " + Protocol.Error.PLAYER_DISCONNECT + ": Player Disconnected");
	}
}
