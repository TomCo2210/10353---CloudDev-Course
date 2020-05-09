package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COUNTRIES") 
public class Country {
	private String countryCode;
	private String countryName;
	
	public Country(String countryCode, String countryName) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
	}
	
	public Country() {
		
	}
	
	@Id 
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
