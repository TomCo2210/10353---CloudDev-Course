package com.example.demo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface CustomerCrud extends PagingAndSortingRepository<Customer, String>{ 
	public List<Customer> findAllByLast(
			@Param("last") String last, 
			Pageable pageable);
	
	public List<Customer> findAllByCountryCountryCode(
			@Param("countryCode") String countryCode, 
			Pageable pageable);
	
	public List<Customer> findByBirthdateBefore(
			@Param("birthdate") LocalDate birthdate, 
			Pageable pageable);
}
