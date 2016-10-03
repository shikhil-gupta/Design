package com.gojek.exception;

/**
 * Implemented own exception class for this ParkingSystemA to use across whole application.
 * 
 * @author shikhill.gupta
 *
 */
public class CustomParkingException extends Exception {

	public static final long serialVersionUID = 1L;
	private String message = null;

	public CustomParkingException() {
		super();
	}

	public CustomParkingException(String message) {
		super(message);
		this.message = message;
	}

	public CustomParkingException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
