package com.example.demo;


import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveSongsListController {

	private SongsListsService songsLists;

	@Autowired
	public ReactiveSongsListController(SongsListsService songsLists) {
		super();
		this.songsLists = songsLists;
	}

	@RequestMapping(
			path = "/lists",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<SongsListDTO> create(
			@RequestBody SongsListDTO songsList) {
		return this.songsLists
				.create(songsList.toEntity())
				.map(SongsListDTO::new);
	}

	@RequestMapping(
			path = "/lists/{listId}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<SongsListDTO> getSongsListById(
			@PathVariable("listId") String listId) {
			return this.songsLists
					.getSongsListById(listId)
					.map(SongsListDTO::new);
	}

	@RequestMapping(
			path = "/lists/{listId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Void> updateSongsListById(
			@PathVariable("listId") String listId,
			@RequestBody SongsListDTO songsList) {
		// TODO: updateSongsListById #3
		// TODO: unMarkSongsListByIdAsDeleted #12
		if (!songsList.isDeleted())
			return this.songsLists
					.updateSongsListById(listId, songsList.toEntity());
		else
			return Mono.empty();
	}

	@RequestMapping(
			path = "/lists/{listId}/songs",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Void> addSongToListById(
			@PathVariable("listId") String listId,
			@RequestBody SongDTO song) {
		// TODO: addSongToListById #4
		return this.songsLists
				.addSongToListById(listId,song.toEntity());	
	}

	@RequestMapping(
			path = "/lists/{listId}/songs/{songId}",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteSongByIdFromListById(
			@PathVariable("listId") String listId,
			@PathVariable("songId") String songId) {
		// TODO: deleteSongByIdFromListById #5
		return this.songsLists
				.deleteSongByIdFromListById(listId,songId);	
	}
	
	@RequestMapping(
			path = "/lists/{listId}/songs",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongDTO> getAllSongsFromSongsListById(
			@PathVariable("listId") String listId,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "songId") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		// TODO: getAllSongsFromSongsListById #6
		return this.songsLists
				.getAllSongsFromSongsListById(listId,sortAttr,orderAttr)
				.map(SongDTO::new);
	}

	@RequestMapping(path = "/lists", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsListDTO> getAllSongsLists(
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		// TODO: getAllSongsLists #7
		return this.songsLists
				.getAllSongsLists(sortAttr,orderAttr)
				.map(SongsListDTO::new);
	}

	@RequestMapping(
			path = "/lists/byUser/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsListDTO> getAllSongsListsByUser(
			@PathVariable(name = "userEmail") String userEmail,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		// TODO: getAllSongsListsByUser #8
		return this.songsLists
				.getAllSongsListsByUser(userEmail, sortAttr, orderAttr)
				.map(SongsListDTO::new);
	}

	@RequestMapping(
			path = "/lists/bySongId/{songId}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsListDTO> getAllSongsListsContainsSongById(
			@PathVariable(name = "songId") String songId,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		// TODO: getAllSongsListsContainsSongById #9
		return this.songsLists
				.getAllSongsListsContainsSongById(songId, sortAttr, orderAttr)
				.map(SongsListDTO::new);
	}

	@RequestMapping(
			path = "/lists",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteAllSongsLists() {
		// TODO: deleteAllSongsLists #10
		return this.songsLists
				.deleteAllSongsLists();
	}

	@RequestMapping(
			path = "/lists/{listId}",
			method = RequestMethod.DELETE)
	public Mono<Void> markSongsInSongsListByIdAsDeleted(
			@PathVariable(name = "listId") String listId) {
		// TODO: markSongsListByIdAsDeleted #11
		return this.songsLists
				.markSongsListByIdAsDeleted(listId);
	}

	//MARK: exception handlers
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	// Error code 404
	public Map<String, Object> handleException(NotFoundException e) {
		return Collections.singletonMap("Error", (e.getMessage() != null) ? e.getMessage() : "Not found");
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	// Error code 500
	public Map<String, Object> handleException(AlreadyExistsException e) {
		return Collections.singletonMap("Error", (e.getMessage() != null) ? e.getMessage() : "Already Exists");
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	// Error code 400
	public Map<String, Object> handleException(BadDataException e) {
		return Collections.singletonMap("Error", (e.getMessage() != null) ? e.getMessage() : "Bad data");
	}

	
//	
}
