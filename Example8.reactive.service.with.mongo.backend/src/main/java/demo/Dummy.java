package demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dummies")
public class Dummy {
	@Id
	private String id;
	private String data;
	@DBRef
	private Set<Dummy> otherDummies;
	
	public Dummy() {
		this.otherDummies = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public Set<Dummy> getOtherDummies() {
		return otherDummies;
	}
	
	public void addSingleOtherDummy(Dummy other) {
		this.otherDummies.add(other);
	}
	
	public void setOtherDummies(Set<Dummy> otherDummies) {
		this.otherDummies = otherDummies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dummy other = (Dummy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
