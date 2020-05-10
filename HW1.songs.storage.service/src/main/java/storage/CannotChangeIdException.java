package storage;

public class CannotChangeIdException extends RuntimeException {

	private static final long serialVersionUID = -2668774162659799258L;

	public CannotChangeIdException(String message) {
		super(message);
	}

	public CannotChangeIdException(Throwable cause) {
		super(cause);
	}

	public CannotChangeIdException(String message, Throwable cause) {
		super(message, cause);
	}
}
