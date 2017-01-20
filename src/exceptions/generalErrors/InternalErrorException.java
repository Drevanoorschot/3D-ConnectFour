package exceptions.generalErrors;

public class InternalErrorException extends Exception {
	private static final long serialVersionUID = -7533602434001175085L;
	
	public InternalErrorException() {
		super("ERROR 000: An internal error occured");
	}

}
