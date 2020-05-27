package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SongsListsServiceImpl implements SongsListsService {

	public static final String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	private SongsListsServiceCrud songsLists;
	
	@Autowired
	public SongsListsServiceImpl(SongsListsServiceCrud songsLists) {
		super();
		this.songsLists = songsLists;
	}

	@Override
	public Mono<SongsList> create(SongsList songsList) {
		return this.songsLists.save(songsList);
	}

	@Override
	public Mono<SongsList> getSongsListById(String listId) {
		return this.songsLists.findById(listId);
	}

	@Override
	public Mono<Void> updateSongToListById(String listId, SongsList songsList) {
		return this.songsLists
				.findById(listId) // Mono<Dummy>
				.flatMap(oldSongsLists->{
					oldSongsLists.setData(songsList);
					return this.songsLists.save(oldSongsLists);
				})// Mono<Dummy>
				.flatMap(d->Mono.empty());// Mono<Void>
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
