package com.example.demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface SongsListsServiceCrud extends ReactiveMongoRepository<SongsList, String>{

	Mono<SongsList> findBySongsListId(String songsListId);

}
