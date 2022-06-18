package com.exception;

public class IllegalValueException extends Exception {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public String getMessage(){
	return "Error in the values supplied to the function";
}
}
