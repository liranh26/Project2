package inventory.webservice.rest.app.exceptions;

/*
 * MissingDataException is a custom exception which extends RuntimeException, 
 * it contains a message with additional data about the exception.
 */
public class MissingDataException extends RuntimeException {

	private static final long serialVersionUID = -5487865902901837799L;

	public MissingDataException(String msg) {
		super(msg);
	}
}
