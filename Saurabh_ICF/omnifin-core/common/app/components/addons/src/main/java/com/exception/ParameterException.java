package com.exception;

public class ParameterException extends Exception {

	private static final long serialVersionUID = 8631176346950425734L;

	private ParameterException() {
		super();
	}

	/**
	 * @param message
	 */
	public ParameterException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

}
