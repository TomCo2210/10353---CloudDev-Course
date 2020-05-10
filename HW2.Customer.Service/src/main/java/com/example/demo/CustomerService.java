package com.example.demo;
import java.util.List;

import javassist.NotFoundException;

//implement a higher level REST API layer
//implement this interface using DAL - Data Access Layer
public interface  CustomerService {
	// Create - INSERT
		public Customer create (Customer Customer) throws NotFoundException,BadDataException;
		
		// Read All - SELECT 
		public List<Customer> getAll(int size, int page)throws NotFoundException,BadDataException;
		
		// Read One Item - SELECT
		public Customer getCustomerByEmail (String email)throws NotFoundException,BadDataException;
		
		// Read items - SELECT
		public List<Customer> getCustomersByLastName(String lastName, int size, int page)throws NotFoundException,BadDataException;
		
		// Read items - SELECT
		public List<Customer> getCustomersByCountryCode(String countryCode, int size, int page)throws NotFoundException,BadDataException;
		
		// Read items - SELECT
		public List<Customer> getCustomersByAgeGreaterThan(int age, int page, int size)throws NotFoundException,BadDataException; 
		
		// Update - UPDATE
		public Customer update (String emailToUpdate, Customer update) throws BadDataException, NotFoundException;
		
		// Delete - DELETE
		public void deleteAll ();
		
		// Update - UPDATE
		public void updateCountry(String courtryCode, Country country) throws NotFoundException,BadDataException;
}
