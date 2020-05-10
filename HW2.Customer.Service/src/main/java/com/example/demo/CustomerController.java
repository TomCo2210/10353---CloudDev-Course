package com.example.demo;

import java.util.List;
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

import com.google.common.base.Strings;

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
			@RequestParam(name = "byAgeGreaterThan", required = false, defaultValue = "-1") int byAgeGreaterThan,
			@RequestParam(name = "byLastName", required = false) String byLastName,
			@RequestParam(name = "byCountryCode", required = false) String byCountryCode,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		
		List<Customer> customersList;
		if(byLastName != null) 
			customersList = this.customers.getCustomersByLastName(byLastName, size, page);// List<Customer>
		else 
			if(byCountryCode != null) 
				customersList = this.customers.getCustomersByCountryCode(byCountryCode, size, page);// List<Customer>
			else 
				if(byAgeGreaterThan != -1) 
					customersList = this.customers.getCustomersByAgeGreaterThan(byAgeGreaterThan, page, size);// List<Customer>
				else
					customersList = this.customers.getAll(size, page);
		return 
			customersList
			.stream() // Stream<Customer>
			.map(CustomerBoundary::new)// Stream<CustomerBoundary>
			.collect(Collectors.toList()) // List<CustomerBoundary>
			.toArray(new CustomerBoundary[0]); // CustomerBoundary[]
	}
}
