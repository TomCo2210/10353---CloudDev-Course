package com.example.demo;

import java.time.LocalDate;

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
		if (entity == null)
			return;

		this.country = entity.getCountry();
		this.email = entity.getEmail();
		this.name = new Name(entity.getFirst(), entity.getLast());

		LocalDate date = entity.getBirthdate();
		int year = date.getYear();
		String month = date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue() + "";
		String day = date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : date.getDayOfMonth() + "";

		this.birthdate = day + "-" + month + "-" + year;
	}

	public Customer toEntity() throws BadDataException {
		Customer entity = new Customer();
		entity.setEmail(this.email);
		entity.setFirst(this.name.getFirst());
		entity.setLast(this.name.getLast());
		entity.setCountry(this.country);

		/*
		 * dateAsArray[2] = expectedYear dateAsArray[1] = month dateAsArray[0] = day
		 */
		try {
			String[] dateAsArray = this.birthdate.split("-");
			if (dateAsArray.length < 3)
				throw new RuntimeErrorException(null, "Illegal date");

			String expectedYear = dateAsArray[2];

			String month = dateAsArray[1];

			month = ((Integer.parseInt(month) < 10 && month.length() < 2) ? "0" + month : month);

			String day = dateAsArray[0];
			day = (Integer.parseInt(day) < 10 ? "0" + day : day);

			LocalDate date = LocalDate.parse(expectedYear + "-" + month + "-" + day);

			entity.setBirthdate(date);
		} catch (Exception ex) {
			throw new BadDataException("Given date is not valid- the format is:  dd-MM-yyyy");
		}
		return entity;
	}

}
