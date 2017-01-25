package exceptions.generalErrors;

import main.Protocol;

public class UnknownMethodException extends Exception {
	private static final long serialVersionUID = 5181870818387529592L;

	public UnknownMethodException() {
		super("ERROR " + Protocol.Error.UNKNOWN_METHOD.code + ": Unknown method");
	}

}
