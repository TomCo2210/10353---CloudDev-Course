package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CUSTOMER")
public class CustomerEntity {
	@Id // TODO consider replacing this key with email - ???
	private String id;
	
	private String email;
	private Date birthdate;

	//	private Name name;
	// TODO consider replacing these attributes with a Name reference
	private String first;
	private String last;
	
	private Map<String, Object> customerDetails;

	public CustomerEntity() {
		this.customerDetails = new HashMap<>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
