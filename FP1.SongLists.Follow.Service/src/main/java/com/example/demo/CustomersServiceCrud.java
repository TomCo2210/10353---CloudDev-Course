package com.example.demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomersServiceCrud extends ReactiveMongoRepository<Customer, String>{

}
