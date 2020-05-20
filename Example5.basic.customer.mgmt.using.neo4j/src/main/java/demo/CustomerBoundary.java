package demo;

import java.util.Map;

public class CustomerBoundary {
	private String email;
	private String birthdate;
	private NameBoundary name;
	private Map<String, Object> customerDetails;

	public CustomerBoundary() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public NameBoundary getName() {
		return name;
	}

	public void setName(NameBoundary name) {
		this.name = name;
	}

	public Map<String, Object> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(Map<String, Object> customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	
}
