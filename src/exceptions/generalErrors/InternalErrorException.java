package exceptions.generalErrors;

import main.Protocol;

public class InternalErrorException extends Exception {
	private static final long serialVersionUID = -7533602434001175085L;
	
	public InternalErrorException() {
		super("ERROR " + Protocol.Error.INTERNAL_ERROR + ": An internal error occured");
	}

}
