package com.example.demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SongsServiceCrud 
	extends ReactiveMongoRepository<Song, String> {
	
}
