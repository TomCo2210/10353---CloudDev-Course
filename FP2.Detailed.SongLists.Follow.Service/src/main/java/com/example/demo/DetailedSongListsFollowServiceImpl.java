package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class DetailedSongListsFollowServiceImpl implements DetailedSongListsFollowService {

	@Value("${SongsLists.follow,service.url}")
	private String songsListsFollowURL;
	private final String byCustomer = "/byCustomer/";
	
	@Value("${SongsLists.service.url}")
	private String songsListsURL;
	private final String songs = "/songs/";

	@Override
	public Flux<SongsListDTO> getSongsListsDetailsByCustomer(String customerEmail) {
		return WebClient
				.create(songsListsFollowURL + byCustomer + customerEmail)
				.get()
				.accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve()
				.bodyToFlux(SongsListShortDTO.class)
				.concatMap(songsListShort ->{
					return WebClient
							.create(songsListsURL + songsListShort.getSongsListId())
							.get()
							.retrieve()
							.bodyToFlux(SongsListDTO.class);
					/*
					 * Supposed to be bodyToMono, but in HW3 we had a mistake that 
					 * GET /list/{listId} produced TEXT_EVENT_STREAM instead of APPLICATION_JSON_VALUE
					 */
				});
	}

	@Override
	public Flux<SongsListAndSongPair> getAllSongsByCustomer(String customerEmail) {
		return WebClient
				.create(songsListsFollowURL + byCustomer + customerEmail)
				.get()
				.accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve()
				.bodyToFlux(SongsListShortDTO.class)
				.concatMap(songsListShort ->{
					return WebClient
							.create(songsListsURL + songsListShort.getSongsListId() + songs)
							.get()
							.retrieve()
							.bodyToFlux(SongDTO.class)
							.map(song -> {
								return new SongsListAndSongPair(songsListShort.getSongsListId(), song.getSongId());
							});
				});
	}
}
