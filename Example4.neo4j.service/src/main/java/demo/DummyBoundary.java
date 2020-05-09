package demo;

import java.util.Date;
import java.util.stream.Collectors;

public class DummyBoundary {
	private String id;
	private FullName name;
	private Date dummyDate;
	private String[] subDummies;
	
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
		this.setSubDummies(
		  entity.getSubdummies()
			.stream()
			.map(s->s.getDescription())
			.collect(Collectors.toList())
			.toArray(new String[0]));
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
	
	public String[] getSubDummies() {
		return subDummies;
	}
	
	public void setSubDummies(String[] subDummies) {
		this.subDummies = subDummies;
	}
	
}
