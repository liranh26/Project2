package exceptions;

public class MissingDataException  extends RuntimeException{

	private static final long serialVersionUID = -5487865902901837799L;

	public MissingDataException(String msg) {
		super(msg);
	}
}

