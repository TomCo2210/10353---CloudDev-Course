package com.example.demo;

public class AlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = -5298005387945687228L;

	public AlreadyExistsException() {
		super();
	}

	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyExistsException(String message) {
		super(message);
	}

	public AlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
