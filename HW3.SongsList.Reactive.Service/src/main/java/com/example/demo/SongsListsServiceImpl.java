package com.example.demo;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SongsListsServiceImpl implements SongsListsService {
	private SongsListsServiceCrud songsLists;

	@Autowired
	public SongsListsServiceImpl(SongsListsServiceCrud songsLists) {
		super();
		this.songsLists = songsLists;
	}

	@Override
	public Mono<SongsList> create(SongsList songsList) {
		createNewSongsList(songsList);
		return this.songsLists.save(songsList);
	}

	private void createNewSongsList(SongsList songsList) {
		if (songsList.getId() != null)
			songsList.setId(null);
		songsList.setCreatedTimestamp(Instant.now());
	}
	

	@Override
	public Mono<SongsList> getSongsListById(String listId) {
		return this.songsLists.findById(listId);
	}

	@Override
	public Mono<Void> updateSongsListById(String listId, SongsList songsList) {
		return this.songsLists.findById(listId) // Mono<SongLists>
				.flatMap(oldSongsList -> {
					oldSongsList.setData(songsList);
					return this.songsLists.save(oldSongsList);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	@Override
	public Mono<Void> addSongToListById(String listId, Song song) {
		return this.songsLists.findById(listId) // Mono<SongLists>
				.flatMap(oldSongsLists -> {
					oldSongsLists.addSongList(song);
					return this.songsLists.save(oldSongsLists);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	@Override
	public Mono<Void> deleteSongByIdFromListById(String listId, String songId) {
		return this.songsLists.findById(listId) // Mono<SongLists>
				.flatMap(oldSongsList -> {
					oldSongsList.removeSongById(songId);
					return this.songsLists.save(oldSongsList);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	private List<Song> songs;

	@Override
	public Flux<Song> getAllSongsFromSongsListById(String listId, String sortAttr, String orderAttr) {
		Mono<SongsList> listsong = this.songsLists.findById(listId);

		listsong.subscribe(v -> songs = v.getListContent());
		// TODO sort by sortAttr orderAttr
		// this.songsLists.findAllBycustomer_email(email, Sort.by(sortBy));

		return Flux.fromIterable(songs);

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
		return this.songsLists.deleteAll();
	}

	@Override
	public Mono<Void> markSongsListByIdAsDeleted(String listId) {
		// TODO markSongsInSongsListByIdAsDeleted in SongsListsServiceImpl
		return null;
	}

	@Override
	public Mono<Void> unMarkSongsListByIdAsDeleted(String listId) {
		// TODO unMarkSongsInSongsListByIdAsDeleted in SongsListsServiceImpl
		return null;
	}
}
