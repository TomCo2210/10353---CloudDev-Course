package demo;

import java.util.Date;

public class DummyBoundary {
	private String id;
	private FullName name;
	private Date dummyDate;
	
	public DummyBoundary() {
	}
	
	public DummyBoundary(Dummy entity) {
		this.setDummyDate(entity.getDummyDate());
		if (entity.getId() != null) {
			this.setId(entity.getId().toString());
		}
		if (entity.getFirstName() != null && entity.getLastName() != null) {
			this.setName(new FullName(entity.getFirstName(), entity.getLastName()));
		}
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public FullName getName() {
		return name;
	}
	
	public void setName(FullName name) {
		this.name = name;
	}
	
	public Date getDummyDate() {
		return dummyDate;
	}
	
	public void setDummyDate(Date dummyDate) {
		this.dummyDate = dummyDate;
	}
	
	public Dummy toEntity(){
		Dummy entity = new Dummy();
		
		entity.setDummyDate(this.getDummyDate());
		if (this.getId() != null) {
			entity.setId(Long.parseLong(this.getId()));
		}else {
			entity.setId(null);
		}
		
		if (this.getName() != null) {
			entity.setFirstName(this.getName().getFirstName());
			entity.setLastName(this.getName().getLastName());
		}
		
		return entity;
	}
}
