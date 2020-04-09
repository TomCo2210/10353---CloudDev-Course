package songs;

public class SongNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6251142946410771224L;

	public SongNotFoundException() {
	}

	public SongNotFoundException(String message) {
		super(message);
	}

	public SongNotFoundException(Throwable cause) {
		super(cause);
	}

	public SongNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
