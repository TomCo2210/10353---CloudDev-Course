package com.example.demo;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4530576118308896391L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}
}
