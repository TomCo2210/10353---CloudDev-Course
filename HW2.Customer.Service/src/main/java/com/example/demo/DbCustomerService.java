package com.example.demo;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Customer create(Customer customer) throws RuntimeException {
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
	public Customer getCustomerByEmail(String email) {
		Optional<Customer> optional = this.customers
				.findById(email); // SELECT
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> getCustomersByLastName(String lastName, int size, int page) {
		return this.customers
				.findAllByLast(lastName, PageRequest.of(page, size, Direction.ASC, "email", "email"));
				 // SELECT
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> getCustomersByCountryCode(String countryCode, int size, int page) {
		return this.customers
				.findAllByLast(countryCode, PageRequest.of(page, size, Direction.ASC, "email", "email"));
				 // SELECT
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> getCustomersByAgeGreaterThan(int age, int page, int size) {		
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
	public Customer update(String emailToUpdate, Customer update) throws RuntimeException {
		if(emailToUpdate == null)
			throw new BadDataException("Provided email is empty");
		Customer rv = this.getCustomerByEmail(emailToUpdate);
		if(rv == null)
			throw new NotFoundExcetion("Could not find customer: " + emailToUpdate);
		
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
	public void updateCountry(String courtryCode, Country update) throws NotFoundException{
		Optional<Country> Optionalcountry = countries.findById(courtryCode);
		 if(!Optionalcountry.isPresent())
			throw new NotFoundException("Could not find country: " + courtryCode);
		
		Country country = Optionalcountry.get();
		country.setCountryName(update.getCountryName());
		countries.save(country);	
	}

}
