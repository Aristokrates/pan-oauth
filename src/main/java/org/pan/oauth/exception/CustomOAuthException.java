package org.pan.oauth.exception;

/**
 * Custom exception thrown when open authentication fails
 * 
 * @author Pance.Isajeski
 *
 */
public class CustomOAuthException extends RuntimeException {

	private static final long serialVersionUID = -1042177933288067336L;

	public CustomOAuthException() {
		super();
	}

	public CustomOAuthException(Throwable cause) {
		super(cause);
	}

}
