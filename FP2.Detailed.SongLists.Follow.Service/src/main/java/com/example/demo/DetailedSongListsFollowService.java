package com.example.demo;

import reactor.core.publisher.Flux;

public interface DetailedSongListsFollowService {

	public Flux<SongsListDTO> getSongsListsDetailsByCustomer(String customerEmail);

	public Flux<SongsListAndSongPair> getAllSongsByCustomer(String customerEmail);

}
