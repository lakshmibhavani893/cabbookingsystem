package com.cab.exception;

public class InvalidCancellationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidCancellationException(String message) {
		super(message);
	}

}
