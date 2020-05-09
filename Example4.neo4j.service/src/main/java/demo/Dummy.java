package demo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

// Nodes: DUMMIES 
// attributes: firstName, lastName, <Identifier> id, dummyDate
// Spring Data Neo4J
@NodeEntity(label = "DUMMIES")
public class Dummy {
	@Id @GeneratedValue
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private Date dummyDate;
	
	private Long innerValue;
	
	@Relationship(type = "subs", direction = Relationship.OUTGOING)
	private Set<SubDummy> subdummies;
	
	public Dummy() {
		this.subdummies = new HashSet<>();
	}

	public Dummy(String firstName, String lastName, Date dummyDate) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dummyDate = dummyDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDummyDate() {
		return dummyDate;
	}

	public void setDummyDate(Date dummyDate) {
		this.dummyDate = dummyDate;
	}

	public Long getInnerValue() {
		return innerValue;
	}
	
	public void setInnerValue(Long innerValue) {
		this.innerValue = innerValue;
	}
	
	public Set<SubDummy> getSubdummies() {
		return subdummies;
	}
	
	public void setSubdummies(Set<SubDummy> subdummies) {
		this.subdummies = subdummies;
	}
	
	public void addSubDummy (SubDummy single) {
		this.subdummies.add(single);
	}
}
