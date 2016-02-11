package com.capgemini.stockmarket.common;

public class IllegalOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IllegalOperationException() {
	}

	public IllegalOperationException(String arg0) {
		super(arg0);
	}

	public IllegalOperationException(Throwable arg0) {
		super(arg0);
	}

	public IllegalOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IllegalOperationException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
