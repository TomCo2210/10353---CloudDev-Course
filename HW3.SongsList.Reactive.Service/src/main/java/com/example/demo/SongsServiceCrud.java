package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongsServiceCrud 
	extends ReactiveMongoRepository<Song, String> {
	
	Flux<Song> findAllBySongId(String songId);

	Mono<Song> findBySongId(String songId);
	
	Flux<Song> findAllBySongListId(String listId, Sort by);
}
