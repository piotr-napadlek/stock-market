package com.capgemini.stockmarket.common;

public class IllegalRequestException  extends RuntimeException {
	private static final long serialVersionUID = 1L;

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
