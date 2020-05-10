package com.example.demo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javassist.NotFoundException;

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
	public CustomerBoundary createCustomer(@RequestBody CustomerBoundary customer) throws BadDataException, NotFoundException {
		return new CustomerBoundary(this.customers.create(customer.toEntity()));
	}
	
	@RequestMapping(path = "/customers/{email}"
			, method = RequestMethod.GET
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary getCustomerByEmail(@PathVariable String email) throws NotFoundException {
		Customer customer = this.customers.getCustomerByEmail(email);
		return new CustomerBoundary(customer);
	}
	
	@RequestMapping(path = "/customers/{email}"
			, method = RequestMethod.PUT
			, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@PathVariable("email") String emailToUpdate, @RequestBody CustomerBoundary customer) throws NotFoundException {
			this.customers.update(emailToUpdate, customer.toEntity());
	}
	
	@RequestMapping(path = "/countries/{courtryCode}"
			, method = RequestMethod.PUT
			, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCountry(@PathVariable("courtryCode") String courtryCode, @RequestBody Country country) throws NotFoundException {
			this.customers.updateCountry(courtryCode, country);
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
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) throws BadDataException, NotFoundException {
		
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
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleException(NotFoundException e){
		return Collections
				.singletonMap("error", (e.getMessage() != null)?e.getMessage():"Not found");
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleException(BadDataException e){
		return Collections
				.singletonMap("error", (e.getMessage() != null)?e.getMessage():"Bad data");
	}
}
