package com.davidparry.twitter;

public class ButlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButlerException() {
	}

	public ButlerException(String detailMessage) {
		super(detailMessage);
	}

	public ButlerException(Throwable throwable) {
		super(throwable);
	}

	public ButlerException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
