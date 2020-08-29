package com.example.demo;

public class BadDataException extends RuntimeException {

	private static final long serialVersionUID = -1411433713219581637L;

	public BadDataException() {
		super();
	}

	public BadDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadDataException(String message) {
		super(message);
	}

	public BadDataException(Throwable cause) {
		super(cause);
	}
}
