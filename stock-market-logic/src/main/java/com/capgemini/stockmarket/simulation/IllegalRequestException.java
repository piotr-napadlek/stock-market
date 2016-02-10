package com.capgemini.stockmarket.simulation;

public class IllegalRequestException  extends RuntimeException {

	public IllegalRequestException() {
		super();
	}

	public IllegalRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalRequestException(String message) {
		super(message);
	}

	public IllegalRequestException(Throwable cause) {
		super(cause);
	}
}
