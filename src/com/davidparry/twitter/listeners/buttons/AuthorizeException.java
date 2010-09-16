package com.davidparry.twitter.listeners.buttons;

public class AuthorizeException extends Exception {

	public AuthorizeException() {
	}

	public AuthorizeException(String detailMessage) {
		super(detailMessage);
	}

	public AuthorizeException(Throwable throwable) {
		super(throwable);
	}

	public AuthorizeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
