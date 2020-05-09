package demo;

public class Demo {
	private String id;
	private String name;
	private Integer version;

	public Demo() {
	}

	public Demo(String name, Integer version) {
		super();
		this.name = name;
		this.version = version;
	}

	public Demo(String id, String name, Integer version) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Demo [id=" + id + ", name=" + name + ", version=" + version + "]";
	}

}
