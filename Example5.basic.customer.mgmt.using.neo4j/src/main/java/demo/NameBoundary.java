package demo;

public class NameBoundary {
	private String lastName;
	private String firstName;

	public NameBoundary() {
	}

	public NameBoundary(String lastName, String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public NameBoundary(Name name) {
		if (name != null) {
			this.firstName = name.getFirst();
			this.lastName = name.getLast();
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Name toEntity() {
		Name rv = new Name();
		if (this.firstName != null) {
			rv.setFirst(this.firstName);
		}
		if (this.lastName != null) {
			rv.setLast(this.lastName);
		}
		return rv;
	}
}
