package org.pan.oauth.exception;

/**
 * Custom exception thrown when open id authentication fails
 * 
 * @author Pance.Isajeski
 *
 */
public class CustomOpenIdAuthException extends RuntimeException {

	private static final long serialVersionUID = -506500606146947239L;

	public CustomOpenIdAuthException() {
		super();
	}
	
	public CustomOpenIdAuthException(String message) {
		super("Error occured: [" + message + "]");
	}

	public CustomOpenIdAuthException(Throwable cause) {
		super(cause);
	}
}
