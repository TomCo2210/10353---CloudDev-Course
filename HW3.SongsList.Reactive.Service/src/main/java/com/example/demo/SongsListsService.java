package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongsListsService {

	public Mono<SongsList> create(SongsList songsList);

	public Mono<SongsList> getSongsListById(String listId);

	public Mono<Void> updateSongToListById(String listId, SongsList songsList);

	public Mono<Void> addSongToListById(String listId, SongsList songsList);

	public Mono<Void> deleteSongByIdFromListById(String listId, String songId);

	public Flux<Song> getAllSongsFromSongsListById(String listId, String sortAttr, String orderAttr);

	public Flux<SongsList> getAllSongsLists(String sortAttr, String orderAttr);

	public Flux<SongsList> getAllSongsListsByUser(String userEmail, String sortAttr, String orderAttr);

	public Flux<SongsList> getAllSongsListsContainsSongById(String songId, String sortAttr, String orderAttr);

	public Mono<Void> deleteAllSongsLists();

	public Mono<Void> markSongsInSongsListByIdAsDeleted(String listId);

	public Mono<Void> unMarkSongsInSongsListByIdAsDeleted(String listId);

}