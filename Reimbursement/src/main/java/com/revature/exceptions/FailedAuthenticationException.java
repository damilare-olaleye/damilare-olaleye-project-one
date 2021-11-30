package com.revature.exceptions;

public class FailedAuthenticationException extends Exception {

	public FailedAuthenticationException() {
		super();
	}

	public FailedAuthenticationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FailedAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedAuthenticationException(String message) {
		super(message);
	}

	public FailedAuthenticationException(Throwable cause) {
		super(cause);
	}

}
