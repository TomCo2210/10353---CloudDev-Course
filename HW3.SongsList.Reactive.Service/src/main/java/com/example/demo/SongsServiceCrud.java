package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface SongsServiceCrud 
	extends ReactiveMongoRepository<Song, String> {
	
	Flux<Song> findAllByListId(String listId, Sort sort);
	
}
