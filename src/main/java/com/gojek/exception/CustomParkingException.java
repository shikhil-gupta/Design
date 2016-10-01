package com.gojek.exception;

public class CustomParkingException extends Exception {
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
