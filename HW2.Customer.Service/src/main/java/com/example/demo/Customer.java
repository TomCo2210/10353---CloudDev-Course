package com.example.demo;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMERS") 
public class Customer {
	private String email;
	private String first;
	private String last;
	private LocalDate birthdate;
	private Country country;
	
	public Customer() {
		
	}
	
	public Customer(String email,String first, String last , LocalDate birthdate, Country country) {
		super();
		this.email = email;
		this.first = first;
		this.last = last;
		this.birthdate = birthdate;
		this.country = country;
	}

	@Id
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	@ManyToOne
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

}
