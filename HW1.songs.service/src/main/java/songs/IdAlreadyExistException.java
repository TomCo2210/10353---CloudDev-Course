package songs;

public class IdAlreadyExistException extends RuntimeException {


	private static final long serialVersionUID = 4192316335269355547L;

	public IdAlreadyExistException() {
	}

	public IdAlreadyExistException(String message) {
		super(message);
	}

	public IdAlreadyExistException(Throwable cause) {
		super(cause);
	}

	public IdAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
