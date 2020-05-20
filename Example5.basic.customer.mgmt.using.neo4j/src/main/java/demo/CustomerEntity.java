package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;

// port to other NoSQL DB
@NodeEntity(label = "CUSTOMER")
public class CustomerEntity {
	@Id @GeneratedValue
	private Long id;
	
	private String email;
	private Date birthdate;
//	@Property
//	private Name name;
	
	private String first;
	private String last;
	
	@Properties
	private Map<String, Object> customerDetails;

	public CustomerEntity() {
		this.customerDetails = new HashMap<>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	@JsonIgnore
	public Date getBirthdate() {
		return birthdate;
	}

//	@JsonIgnore
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	// dd-MM-yyyy
//	public String getBirthdate() {
//		return new SimpleDateFormat("dd-MM-yyyy")
//			.format(this.birthdate);
//	}
//
//	public void setBirthdate(String birthdate) {
//		try {
//			this.birthdate = new SimpleDateFormat("dd-MM-yyyy")
//					.parse(birthdate);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

//	public Name getName() {
//		return name;
//	}
//
//	public void setName(Name name) {
//		this.name = name;
//	}
	
	public String getFirst() {
		return first;
	}
	
	public void setFirst(String first) {
		this.first = first;
	}
	
	public String getLast() {
		return last;
	}
	
	public void setLast(String last) {
		this.last = last;
	}
	
	public Map<String, Object> getCustomerDetails() {
		return customerDetails;
	}
	
	public void setCustomerDetails(Map<String, Object> customerDetails) {
		this.customerDetails = customerDetails;
	}

	@Override
	public String toString() {
		return "CustomerEntity [email=" + email + "]";
	}
}
