package com.example.demo;

import java.time.Instant;
import java.util.Comparator;
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

		if (songsList.getId() != null)
			songsList.setId(null);

		if (!Pattern.matches(GlobalVariables.EMAIL_PATTERN, songsList.getUserEmail()))
			throw new BadDataException("Invalid E-mail Address");

		if (songsList.getName() == null || songsList.getName().trim().isEmpty())
			throw new BadDataException("Invalid Name");

		songsList.setCreatedTimestamp(Instant.now());

		return this.songsListsServiceCrud.save(songsList);
	}

	@Override
	public Mono<SongsList> getSongsListById(String listId) {
		return this.songsListsServiceCrud.findById(listId).filter(l -> !l.isDeleted());
	}

	@Override
	public Mono<Void> updateSongsListById(String listId, SongsList songsList) {
		if (!songsList.isDeleted() && songsList.getId() == null && songsList.getName() == null
				&& songsList.getUserEmail() == null) // living songsList or need to revive a songsList
			return this.songsListsServiceCrud.findById(listId) // Mono<SongLists>
					.flatMap(oldSongsList -> {
						oldSongsList.setDeleted(false);
						return this.songsListsServiceCrud.save(oldSongsList);
					}).flatMap(d -> Mono.empty());

		else if (songsList.isDeleted())
			throw new BadDataException("Can Not Update Deleted List");
		else
			return this.songsListsServiceCrud.findById(listId)// Mono<SongLists>
					.filter(l -> !l.isDeleted()).flatMap(oldSongsList -> {
						oldSongsList.setData(songsList);
						return this.songsListsServiceCrud.save(oldSongsList);
					})// Mono<SongLists>
					.flatMap(d -> Mono.empty());// Mono<Void>
	}

	@Override
	public Mono<Void> addSongToListById(String listId, Song song) {
		song.setSongListId(listId);
		return this.songsServiceCrud.save(song).flatMap(newSong -> {
			return this.songsListsServiceCrud.findById(listId).filter(l -> !l.isDeleted()).flatMap(oldSongsLists -> {
				oldSongsLists.addSongList(newSong);
				return this.songsListsServiceCrud.save(oldSongsLists);
			});// Mono<SongLists>
		}).flatMap(d -> Mono.empty());// Mono<Void>;
	}

	@Override
	public Mono<Void> deleteSongByIdFromListById(String listId, String songId) {
		return this.songsListsServiceCrud.findById(listId) // Mono<SongLists>
				.filter(l -> !l.isDeleted()).flatMap(oldSongsList -> {
					Song song = oldSongsList.removeSongById(songId);
					if (song != null)
						this.songsServiceCrud.delete(song);
					return this.songsListsServiceCrud.save(oldSongsList);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	@Override
	public Flux<Song> getAllSongsFromSongsListById(String listId, String sortAttr, String orderAttr) {
		return this.songsListsServiceCrud.findById(listId).filter(l -> !l.isDeleted()).flatMapMany(songList -> {
			if (songList == null)
				return Flux.empty();
			return Flux.fromIterable(songList.getSongs()).sort(new Comparator<Song>() {
				@Override
				public int compare(Song o1, Song o2) {
					switch (sortAttr.toLowerCase()) {
					case "songid":
						return orderAttr.equals("DESC") ? o2.getSongId().compareTo(o1.getSongId())
								: o1.getSongId().compareTo(o2.getSongId());
					default:
						return o1.getSongId().compareTo(o2.getSongId());
					}
				}
			});
		});
	}

	@Override
	public Flux<SongsList> getAllSongsLists(String sortAttr, String orderAttr) {
		return songsListsServiceCrud.findByDeleted(false,
				Sort.by(orderAttr.equals("DESC") ? Direction.DESC : Direction.ASC, sortAttr));
	}

	@Override
	public Flux<SongsList> getAllSongsListsByUser(String userEmail, String sortAttr, String orderAttr) {
		return songsListsServiceCrud
				.findAllByUserEmail(userEmail,
						Sort.by(orderAttr.equals("DESC") ? Direction.DESC : Direction.ASC, sortAttr))
				.filter(L -> !L.isDeleted());

	}

	@Override
	public Flux<SongsList> getAllSongsListsContainsSongById(String songId, String sortAttr, String orderAttr) {
		return this.songsServiceCrud.findAllBySongId(songId).flatMap(song -> {
			return this.songsListsServiceCrud.findById(song.getSongListId()).filter(L -> !L.isDeleted());
		}).sort(new Comparator<SongsList>() {
			@Override
			public int compare(SongsList o1, SongsList o2) {
				switch (sortAttr.toLowerCase()) {
				case "id":
					return orderAttr.equals("DESC") ? 
							o2.getId().compareTo(o1.getId()):
							o1.getId().compareTo(o2.getId());
				case "name":
					return orderAttr.equals("DESC") ? 
							o2.getName().compareTo(o1.getName()):
							o1.getName().compareTo(o2.getName());
				case "useremail":
					return orderAttr.equals("DESC") ?
							o2.getUserEmail().compareTo(o1.getUserEmail()):
							o1.getUserEmail().compareTo(o2.getUserEmail());
				case "createdtimestamp":
					return orderAttr.equals("DESC") ?
							o2.getCreatedTimestamp().compareTo(o1.getCreatedTimestamp()):
							o1.getCreatedTimestamp().compareTo(o2.getCreatedTimestamp());
				default:
					return o1.getId().compareTo(o2.getId());
				}
			}
		});
	}

	@Override
	public Mono<Void> deleteAllSongsLists() {

		return this.songsListsServiceCrud
				.deleteAll()
				.and(
						this.songsServiceCrud
							.deleteAll());
	}

	@Override
	public Mono<Void> markSongsListByIdAsDeleted(String listId) {
		return this.songsListsServiceCrud
				.findById(listId) // Mono<SongLists>
				.filter(L -> !L.isDeleted())
				.flatMap(oldSongsList -> {
					oldSongsList.setDeleted(true);
					return this.songsListsServiceCrud.save(oldSongsList);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}
}
