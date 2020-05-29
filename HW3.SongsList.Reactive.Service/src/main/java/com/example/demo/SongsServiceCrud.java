package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongsServiceCrud 
	extends ReactiveMongoRepository<Song, String> {
	
	Flux<Song> findBySongId(String songId, Sort by);

	Mono<Song> findBySongId(String songId);
	
	Mono<SongsList> findAllBySongId(String songId,Sort by);

	Flux<Song> findAllBySongListId(String listId, Sort by);
}
