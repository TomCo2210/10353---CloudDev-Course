package demo;

public class DummyNotFoundExcetion extends RuntimeException{
	private static final long serialVersionUID = 4720377680252137434L;

	public DummyNotFoundExcetion() {
		super();
	}

	public DummyNotFoundExcetion(String message, Throwable cause) {
		super(message, cause);
	}

	public DummyNotFoundExcetion(String message) {
		super(message);
	}

	public DummyNotFoundExcetion(Throwable cause) {
		super(cause);
	}

}
