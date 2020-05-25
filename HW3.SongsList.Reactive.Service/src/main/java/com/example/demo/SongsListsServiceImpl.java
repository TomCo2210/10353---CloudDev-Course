package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SongsListsServiceImpl implements SongsListsService {

	public static final String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	@Override
	public Mono<SongsList> create(SongsList songsList) {
		// TODO create in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<SongsList> getSongsListById(String listId) {
		// TODO getSongsListById in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<Void> updateSongToListById(String listId, SongsList songsList) {
		// TODO updateSongToListById in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<Void> addSongToListById(String listId, SongsList songsList) {
		// TODO addSongToListById in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<Void> deleteSongByIdFromListById(String listId, String songId) {
		// TODO deleteSongByIdFromListById in SongsListsServiceImpl
		return null;
	}

	@Override
	public Flux<Song> getAllSongsFromSongsListById(String listId, String sortAttr, String orderAttr) {
		// TODO getAllSongsFromSongsListById in SongsListsServiceImpl
		return null;
	}

	@Override
	public Flux<SongsList> getAllSongsLists(String sortAttr, String orderAttr) {
		// TODO getAllSongsLists in SongsListsServiceImpl
		return null;
	}

	@Override
	public Flux<SongsList> getAllSongsListsByUser(String userEmail, String sortAttr, String orderAttr) {
		// TODO getAllSongsListsByUser in SongsListsServiceImpl
		return null;
	}

	@Override
	public Flux<SongsList> getAllSongsListsContainsSongById(String songId, String sortAttr, String orderAttr) {
		// TODO getAllSongsListsContainsSongById in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<Void> deleteAllSongsLists() {
		// TODO deleteAllSongsLists in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<Void> markSongsInSongsListByIdAsDeleted(String listId) {
		// TODO markSongsInSongsListByIdAsDeleted in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<Void> unMarkSongsInSongsListByIdAsDeleted(String listId) {
		// TODO unMarkSongsInSongsListByIdAsDeleted in SongsListsServiceImpl
		return null;
	}
}
