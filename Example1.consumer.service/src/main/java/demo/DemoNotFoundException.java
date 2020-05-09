package demo;

public class DemoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6251142946410771224L;

	public DemoNotFoundException() {
	}

	public DemoNotFoundException(String message) {
		super(message);
	}

	public DemoNotFoundException(Throwable cause) {
		super(cause);
	}

	public DemoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
