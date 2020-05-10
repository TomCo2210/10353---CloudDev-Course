package storage;

public class DataIncompleteException extends RuntimeException{


	private static final long serialVersionUID = 8921553311657422520L;

	
	public DataIncompleteException() {
		super();
	}

	public DataIncompleteException(String message) {
		super(message);
	}

	public DataIncompleteException(Throwable cause) {
		super(cause);
	}

	public DataIncompleteException(String message, Throwable cause) {
		super(message, cause);
	}
}
