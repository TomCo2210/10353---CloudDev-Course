package demo;

public class Demo {
	private String message;
	private String id;

	public Demo() {
	}

	public Demo(Dummy dummy) {
		this.id = dummy.getId();
		this.message = dummy.getData();
	}
	
	public Dummy toDummy() {
		Dummy rv = new Dummy();
		rv.setId(this.id);
		rv.setData(this.message);
		return rv;
	}

	public Demo(String massage) {
		this.message = massage;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String massage) {
		this.message = massage;
	}

	@Override
	public String toString() {
		return "Demo [massage=" + message + ", id=" + id + "]";
	}

	
}
