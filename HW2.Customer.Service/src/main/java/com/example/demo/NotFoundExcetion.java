package com.example.demo;

public class NotFoundExcetion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundExcetion() {
		super();
	}

	public NotFoundExcetion(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundExcetion(String message) {
		super(message);
	}

	public NotFoundExcetion(Throwable cause) {
		super(cause);
	}
}
