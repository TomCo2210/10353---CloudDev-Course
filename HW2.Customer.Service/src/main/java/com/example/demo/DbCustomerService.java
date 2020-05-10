package com.example.demo;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import javassist.NotFoundException;

@Service
public class DbCustomerService implements CustomerService{
	private CustomerCrud customers;
	private	CountryCrud countries;
	
	@Autowired
	public DbCustomerService(CustomerCrud customers, CountryCrud countries) {
		super();
		this.customers = customers;
		this.countries = countries;
	}
	
	@Override
	public Customer create(Customer customer) throws NotFoundException,BadDataException {
		if(customer == null)
			throw new BadDataException("Customer is not valid");

		if(Strings.isNullOrEmpty(customer.getEmail()))
			throw new BadDataException("Email bust be provided");
		
		Optional<Customer> optional = this.customers
				.findById(customer.getEmail()); // SELECT
		
		if (optional.isPresent()) 
			throw new BadDataException("Customer already exists");
		
		if(customer.getCountry() == null)
			throw new BadDataException("Customer must include country");
		
		if(customer.getCountry().getCountryCode() == null)
			throw new BadDataException("Country must include country code");
		
		Optional<Country> Optionalcountry = countries.findById(customer.getCountry().getCountryCode());
		Country country;
		
		if(Optionalcountry.isPresent())//update
			country = Optionalcountry.get();
		else { //create		
			if(customer.getCountry().getCountryName() == null)
				throw new BadDataException("Country must include country name");

			country = new Country(customer.getCountry().getCountryCode()
											,customer.getCountry().getCountryName());		
		}
		
		customer.setCountry(countries.save(country));	
		countries.save(customer.getCountry());
		return this.customers
			.save(customer); // INSERT / SELECT + UPDATE = UPSERT
	}

	@Override
	public List<Customer> getAll(int size, int page) {
		return this.customers
				.findAll(PageRequest.of(page, size, Direction.ASC, "email"))
				.getContent();
	}

	@Override
	@Transactional(readOnly = true)
	public Customer getCustomerByEmail(String email) throws NotFoundException,BadDataException{
		if(Strings.isNullOrEmpty(email))
			throw new BadDataException("Email bust be provided");
		
		Optional<Customer> optional = this.customers
				.findById(email); // SELECT
		
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new NotFoundException("Could not find customer: " + email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> getCustomersByLastName(String lastName, int size, int page)throws NotFoundException,BadDataException {
		if(lastName == null)
			throw new BadDataException("Provided last name is empty");
		
		return this.customers
				.findAllByLast(lastName, PageRequest.of(page, size, Direction.ASC, "email", "email"));
				 // SELECT
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> getCustomersByCountryCode(String countryCode, int size, int page)throws NotFoundException,BadDataException {
		if(countryCode == null)
			throw new BadDataException("Provided country code is empty");
		
		return this.customers
				.findAllByCountryCountryCode(countryCode, PageRequest.of(page, size, Direction.ASC, "email", "email"));
				 // SELECT
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> getCustomersByAgeGreaterThan(int age, int page, int size) throws NotFoundException,BadDataException{		
		String expectedYear = Year.now().getValue() - age + "";
		
		String month = YearMonth.now().getMonthValue() + "";
		month = (Integer.parseInt(month)<10? "0" + month :month);
		
		String day = MonthDay.now().getDayOfMonth() + "";
		day = (Integer.parseInt(day)<10? "0" + day :day);
		
		LocalDate date = LocalDate.parse(expectedYear+"-"+ month +"-"+day);
		return this.customers
				.findByBirthdateBefore(date, PageRequest.of(page, size, Direction.ASC, "email", "email"));
		
	}

	@Override
	@Transactional
	public Customer update(String emailToUpdate, Customer update)throws NotFoundException,BadDataException{
		if(emailToUpdate == null)
			throw new BadDataException("Provided email is empty");
		
		Customer rv;
		try {
			rv = this.getCustomerByEmail(emailToUpdate);
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		}
		
		if(rv == null)
			throw new NotFoundException("Could not find customer: " + emailToUpdate);
		
		if (update.getBirthdate() != null) {
			rv.setBirthdate(update.getBirthdate());
		}
		
		if (update.getFirst() != null
				&& update.getLast() != null) {
			rv.setFirst(update.getFirst());
			rv.setLast(update.getLast());
		}
		
		if(update.getCountry() != null) 
			if(update.getCountry().getCountryCode() != null && update.getCountry().getCountryName() != null) {
				Optional<Country> Optionalcountry = countries.findById(update.getCountry().getCountryCode());
				Country country;
				if(Optionalcountry.isPresent())//update
					country = Optionalcountry.get();
				else //create
					country = new Country(update.getCountry().getCountryCode()
													,update.getCountry().getCountryName());
				rv.setCountry(countries.save(country));
			}
		return this.customers
			.save(rv); // SELECT INSERT / **UPDATE**
	}

	@Override
	@Transactional
	public void deleteAll() {
		 customers.deleteAll(); // DELETE;
		 countries.deleteAll(); // DELETE
	}

	@Override
	@Transactional
	public void updateCountry(String courtryCode, Country update)throws NotFoundException,BadDataException{
		Optional<Country> Optionalcountry = countries.findById(courtryCode);
		 if(!Optionalcountry.isPresent())
			throw new NotFoundException("Could not find country: " + courtryCode);
		
		Country country = Optionalcountry.get();
		country.setCountryName(update.getCountryName());
		countries.save(country);	
	}

}
