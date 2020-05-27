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
	public Mono<SongsList> create(
			@RequestBody SongsList songsList) {
		// TODO: create #1
		this.songsLists.create(songsList);
		return Mono.empty();
	}

	@RequestMapping(
			path = "/lists/{listId}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<SongsList> getSongsListById(
			@PathVariable("listId") String listId) {
		// TODO: getSongsListById #2
		return Mono.empty();
	}

	@RequestMapping(
			path = "/lists/{listId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Void> updateSongToListById(
			@PathVariable("listId") String listId,
			@RequestBody SongsList songsList) {
		// TODO: updateSongToListById #3
		return Mono.empty();
	}
	
	@RequestMapping(
			path = "/lists/{listId}/songs",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Void> addSongToListById(
			@PathVariable("listId") String listId,
			@RequestBody SongsList songsList) {
		// TODO: addSongToListById #4
		return Mono.empty();
	}

	@RequestMapping(
			path = "/lists/{listId}/songs/{songId}",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteSongByIdFromListById(
			@PathVariable("listId") String listId,
			@PathVariable("songId") String songId) {
		// TODO: deleteSongByIdFromListById #5
		return Mono.empty();
	}

	@RequestMapping(
			path = "/lists/{listId}/songs?sortAttr={sortAttr}&orderAttr={orderAttr}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Song> getAllSongsFromSongsListById(
			@PathVariable("listId") String listId,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "songId") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		// TODO: getAllSongsFromSongsListById #6
		return Flux.empty();
	}

	@RequestMapping(
			path = "/lists?sortAttr={sortAttr}&orderAttr={orderAttr}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsList> getAllSongsLists(
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		// TODO: getAllSongsLists #7
		return Flux.empty();
	}

	@RequestMapping(
			path="/lists/byUser/{userEmail}?sortAttr={sortAttr}&orderAttr={orderAttr}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsList> getAllSongsListsByUser(
			@PathVariable(name = "userEmail") String userEmail,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		//TODO: getAllSongsListsByUser #8
		return Flux.empty();
	}

	@RequestMapping(
			path="/lists/bySongId/{songId}?sortAttr={sortAttr}&orderAttr={orderAttr",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsList> getAllSongsListsContainsSongById(
			@PathVariable(name = "songId") String songId,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr) {
		//TODO: getAllSongsListsContainsSongById #9
		return Flux.empty();
	}

	@RequestMapping(
			path = "/lists",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteAllSongsLists() {
		// TODO: deleteAllSongsLists #10
		return Mono.empty();
	}

	@RequestMapping(
			path = "/lists/{listId}",
			method = RequestMethod.DELETE)
	public Mono<Void> markSongsInSongsListByIdAsDeleted(
			@PathVariable(name = "listId") String listId
			) {
		// TODO: markSongsListByIdAsDeleted #11
		return Mono.empty();
	}
	
	@RequestMapping(
			path = "/lists/{listId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Void> unMarkSongsInSongsListByIdAsDeleted(
			@PathVariable(name = "listId") String listId
			) {
		// TODO: unMarkSongsListByIdAsDeleted #12
		return Mono.empty();
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

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	// Error code 400
	public Map<String, Object> handleException(IllegalInputException e) {
		return Collections.singletonMap("Error", (e.getMessage() != null) ? e.getMessage() : "Illegal Input");
	}
	
}
