package com.example.demo;

import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	private CustomerService customers;

	@Autowired
	public CustomerController(CustomerService customers) {
		this.customers = customers;
	}
	
	@RequestMapping(path = "/customers"
			, method = RequestMethod.POST
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary createCustomer(@RequestBody CustomerBoundary customer) {
		if (this.customers.getCustomerByEmail(customer.getEmail()) != null){
			throw new RuntimeException("customer already exists with email: " + customer.getEmail());
		}
		return new CustomerBoundary(this.customers.create(customer.toEntity()));
	}
	
	@RequestMapping(path = "/customers/{email}"
			, method = RequestMethod.GET
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary getCustomerByEmail(@PathVariable String email) {
		Customer customer = this.customers.getCustomerByEmail(email);
		if(customer == null)
			throw new RuntimeErrorException(null,"Customer not found");
		return new CustomerBoundary(customer);
	}
	
	@RequestMapping(path = "/customers/{email}"
			, method = RequestMethod.PUT
			, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@PathVariable("email") String emailToUpdate, @RequestBody CustomerBoundary customer) {
			this.customers.update(emailToUpdate, customer.toEntity());
	}
	
	// Delete - DELETE
		@RequestMapping(path = "/customers",
				method = RequestMethod.DELETE)
		public void deleteAll () {
			this.customers
				.deleteAll();
		}	
		
	// Read All - SELECT 
	@RequestMapping(path = "/customers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary[] getAll(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return 
		  this.customers
			.getAll(size, page) // List<Customer>
			.stream() // Stream<Customer>
			.map(CustomerBoundary::new)// Stream<CustomerBoundary>
			.collect(Collectors.toList()) // List<CustomerBoundary>
			.toArray(new CustomerBoundary[0]); // CustomerBoundary[]
	}
	
	// Read All - SELECT 
	@RequestMapping(path = "/customers?byLastName={byLastName}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary[] getCustomersByLastName(
			@RequestParam(name = "byLastName", required = true) String byLastName,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return 
		  this.customers
			.getCustomersByLastName(byLastName, size, page)// List<Customer>
			.stream() // Stream<Customer>
			.map(CustomerBoundary::new)// Stream<CustomerBoundary>
			.collect(Collectors.toList()) // List<CustomerBoundary>
			.toArray(new CustomerBoundary[0]); // CustomerBoundary[]
	}
	
	// Read All - SELECT 
	@RequestMapping(path = "/customers?byAgeGreaterThan={byAgeGreaterThan}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary[] getCustomersByAgeGreaterThan(
			@RequestParam(name = "byAgeGreaterThan", required = true) int byAgeGreaterThan,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return 
		  this.customers
			.getCustomersByAgeGreaterThan(byAgeGreaterThan, page, size)// List<Customer>
			.stream() // Stream<Customer>
			.map(CustomerBoundary::new)// Stream<CustomerBoundary>
			.collect(Collectors.toList()) // List<CustomerBoundary>
			.toArray(new CustomerBoundary[0]); // CustomerBoundary[]
	}
	
	// Read All - SELECT 
	@RequestMapping(path = "/customers?byCountryCode={byCountryCode}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary[] getCustomersByCountryCode(
			@RequestParam(name = "byCountryCode", required = true) String byCountryCode,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return 
		  this.customers
			.getCustomersByCountryCode(byCountryCode, size, page)// List<Customer>
			.stream() // Stream<Customer>
			.map(CustomerBoundary::new)// Stream<CustomerBoundary>
			.collect(Collectors.toList()) // List<CustomerBoundary>
			.toArray(new CustomerBoundary[0]); // CustomerBoundary[]
	}
	
	

}
