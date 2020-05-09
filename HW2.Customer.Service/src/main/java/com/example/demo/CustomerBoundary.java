package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.management.RuntimeErrorException;

public class CustomerBoundary {
	private String email;
	private Name name;
	private String birthdate;
	private Country country;

	public CustomerBoundary() {
		
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public CustomerBoundary(Customer entity) {
		if(entity == null)
			return;
		this.country = entity.getCountry();
		this.email = entity.getEmail();
		this.name = new Name(entity.getFirst(),entity.getLast());		
		LocalDate date = entity.getBirthdate();
		this.birthdate = date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();
	}
	public Customer toEntity(){
		Customer entity = new Customer();
		entity.setEmail(this.email);
		entity.setFirst(this.name.getFirst());
		entity.setLast(this.name.getLast());
		entity.setCountry(this.country);
		String [] dateAsArray =  this.birthdate.split("-");
		if(dateAsArray.length < 3 )
			throw new RuntimeErrorException(null, "Illegal date");
		LocalDate date = LocalDate.parse(Integer.parseInt(dateAsArray[2]) + "-" + Integer.parseInt(dateAsArray[1]) + "-" + Integer.parseInt(dateAsArray[0]));
		
		entity.setBirthdate(date);
		return entity;
	}

	
}
