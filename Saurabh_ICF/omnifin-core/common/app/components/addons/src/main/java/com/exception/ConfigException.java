/**
 * 
 */
package com.exception;

/**
 * @author Admin
 * 
 */
public class ConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8023077423911159672L;

	/**
	 * 
	 */
	private ConfigException() {

	}

	/**
	 * @param message
	 */
	public ConfigException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public ConfigException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigException(String message, Throwable cause) {
		super(message, cause);

	}

}
