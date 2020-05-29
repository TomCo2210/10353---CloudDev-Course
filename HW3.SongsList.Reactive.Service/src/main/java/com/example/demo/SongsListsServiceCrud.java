package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongsListsServiceCrud 
	extends ReactiveMongoRepository<SongsList, String>{

	Mono<SongsList> findByIdAndDeleted(String listId, boolean deleted);

	Flux<SongsList> findByDeleted(boolean deleted,Sort sort);
	
	Flux<SongsList> findAllByUserEmail(String userEmail,Sort sort);
	
	Flux<SongsList> findAllBySongsSongId(String songId,Sort sort);
}
