package demo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

// TABLES: DUMMIES
// COLUMNS: FIRST_NAME, LAST_NAME, <PK> ID, DUMMY_DATE
// Spring Data JPA + JPA: Hibernate
@Entity
@Table(name="DUMMIES")
public class Dummy {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dummyDate;

	public Dummy() {
	}

	public Dummy(String firstName, String lastName, Date dummyDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dummyDate = dummyDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@Lob // Large OBject
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Temporal(TemporalType.DATE)
	public Date getDummyDate() {
		return dummyDate;
	}

	public void setDummyDate(Date dummyDate) {
		this.dummyDate = dummyDate;
	}

}
