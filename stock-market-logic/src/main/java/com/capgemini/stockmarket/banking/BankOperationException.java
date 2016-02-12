package com.capgemini.stockmarket.banking;

public class BankOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BankOperationException() {
		super();
	}

	public BankOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BankOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankOperationException(String message) {
		super(message);
	}

	public BankOperationException(Throwable cause) {
		super(cause);
	}

}
