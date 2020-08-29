package com.example.demo;

public class CustomerDTO {
	private String customerEmail;

	public CustomerDTO() {
		super();
	}

	public CustomerDTO(String customerEmail) {
		super();
		this.customerEmail = customerEmail;
	}

	public CustomerDTO(Customer customer) {
		super();
		this.customerEmail = customer.getCustomerEmail();
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Customer toEntity() {
		return new Customer(this.customerEmail);
	}
}
