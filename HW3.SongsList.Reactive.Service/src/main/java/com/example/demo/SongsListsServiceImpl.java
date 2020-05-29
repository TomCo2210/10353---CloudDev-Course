package com.example.demo;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SongsListsServiceImpl implements SongsListsService {
	private SongsListsServiceCrud songsListsServiceCrud;
	private SongsServiceCrud songsServiceCrud;

	@Autowired
	public SongsListsServiceImpl(SongsListsServiceCrud songsLists, SongsServiceCrud songsServiceCrud) {
		super();
		this.songsListsServiceCrud = songsLists;
		this.songsServiceCrud = songsServiceCrud;
	}

	@Override
	public Mono<SongsList> create(SongsList songsList) {
		createNewSongsList(songsList);
		if (!Pattern.matches(GlobalVariables.EMAIL_PATTERN, songsList.getUserEmail())) {
			throw new BadDataException("Invalid E-mail Address");
		}
		if (songsList.getName() == null || songsList.getName().trim().isEmpty()) {
			throw new BadDataException("Invalid Name");
		}
		return this.songsListsServiceCrud.save(songsList);
	}

	private void createNewSongsList(SongsList songsList) {
		if (songsList.getId() != null)
			songsList.setId(null);
		songsList.setCreatedTimestamp(Instant.now());
	}
	

	@Override
	public Mono<SongsList> getSongsListById(String listId) {
		return this.songsListsServiceCrud.findByIdAndDeleted(listId,false);
	}

	@Override
	public Mono<Void> updateSongsListById(String listId, SongsList songsList) {
		return this.songsListsServiceCrud.findByIdAndDeleted(listId,false) // Mono<SongLists>
				.flatMap(oldSongsList -> {
					oldSongsList.setData(songsList);
					return this.songsListsServiceCrud.save(oldSongsList);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	@Override
	public Mono<Void> addSongToListById(String listId, Song song) {
		return this.songsServiceCrud.save(song)
				.flatMap(newSong->{
					return this.songsListsServiceCrud.findByIdAndDeleted(listId,false)
					.flatMap(oldSongsLists -> {
						oldSongsLists.addSongList(newSong);
						return this.songsListsServiceCrud.save(oldSongsLists);
					});// Mono<SongLists>
				})
				.flatMap(d -> Mono.empty());// Mono<Void>;
	}

	@Override
	public Mono<Void> deleteSongByIdFromListById(String listId, String songId) {
		return this.songsListsServiceCrud.findByIdAndDeleted(listId,false) // Mono<SongLists>
				.flatMap(oldSongsList -> {
					oldSongsList.removeSongById(songId);
					return this.songsListsServiceCrud.save(oldSongsList);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	private List<Song> songs;

	@Override
	public Flux<Song> getAllSongsFromSongsListById(String listId, String sortAttr, String orderAttr) {
		return this.songsListsServiceCrud.findByIdAndDeleted(listId,false, Sort.by(orderAttr.equals("DESC")?Direction.DESC:Direction.ASC, sortAttr))
				
				;
		/*if(songList == null)
			return Flux.empty();
		songList.subscribe(v -> songs = v.getListContent());*/

		// TODO sort by sortAttr orderAttr
		// this.songsLists.findAllBycustomer_email(email, Sort.by(sortBy));
		//Flux.fromIterable (Arrays.asList(students)).sort( (obj1, obj2) -> obj1.getName().compareTo(obj2.getName()));

		/*return Flux.fromIterable(songs).sort(new Comparator<Song>() {
			@Override
			public int compare(Song first, Song second) {
				switch (sortAttr) {
				case "songId":
					if(orderAttr == "ASC")
						return first.getSongId().compareTo(second.getSongId());
					return second.getSongId().compareTo(first.getSongId()); 
				default:
					return 1;
				}
			}
		});*/

	}

	@Override
	public Flux<SongsList> getAllSongsLists(String sortAttr, String orderAttr) {
		return songsListsServiceCrud.findByDeleted(false,Sort.by(orderAttr.equals("DESC")?Direction.DESC:Direction.ASC, sortAttr));
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
		return this.songsListsServiceCrud
				.deleteAll();
	}

	@Override
	public Mono<Void> markSongsListByIdAsDeleted(String listId) {
		return this.songsListsServiceCrud.findByIdAndDeleted(listId,false) // Mono<SongLists>
				.flatMap(oldSongsList -> {
					oldSongsList.setDeleted(true);
					return this.songsListsServiceCrud.save(oldSongsList);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	@Override
	public Mono<Void> unMarkSongsListByIdAsDeleted(String listId) {
		// TODO unMarkSongsInSongsListByIdAsDeleted in SongsListsServiceImpl
		return null;
	}
}
