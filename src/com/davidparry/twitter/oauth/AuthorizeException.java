package com.davidparry.twitter.oauth;

public class AuthorizeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9133385162364892648L;

	public AuthorizeException() {
		super();
	}

	public AuthorizeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public AuthorizeException(String detailMessage) {
		super(detailMessage);
	}

	public AuthorizeException(Throwable throwable) {
		super(throwable);
	}

}
