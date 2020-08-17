package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveSongListsFollowController {
	
	private SongListsFollowService songListsFollowService;

	@Autowired
	public ReactiveSongListsFollowController(SongListsFollowService songListsFollowService) {
		super();
		this.songListsFollowService = songListsFollowService;
	}

	@RequestMapping(
			path = "/follow/{customerEmail}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Void> addSongsListToCustomerByCustomerEmail(
			@RequestBody SongsList songsList,
			@PathVariable("customerEmail") String customerEmail) {
		return  this.songListsFollowService
				.addSongsListToCustomerByCustomerEmail(customerEmail,songsList);	
	}
	
	@RequestMapping(
			path = "/follow/{customerEmail}/{listId}",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteListByIdFromCustomerByCustomerEmail(
			@PathVariable("customerEmail") String customerEmail,
			@PathVariable("listId") String listId) {
			return this.songListsFollowService
					.deleteListByIdFromCustomerByCustomerEmail(customerEmail,listId);
	}

	@RequestMapping(
			path = "/byListAndCustomer/{customerEmail}/{listId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<CustomerAndSongsListPairDTO> getByListAndCustomer(
			@PathVariable("customerEmail") String customerEmail,
			@PathVariable("listId") String listId){
		return this.songListsFollowService.
				getByListAndCustomer(customerEmail,listId);
	}
	
	@RequestMapping(
			path = "/byCustomer/{customerEmail}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsList> getAllSongsListsByCustomerEmail(
			@PathVariable("customerEmail") String customerEmail,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr){
		return this.songListsFollowService
				.getAllSongsListsByCustomerEmail(customerEmail, sortAttr, orderAttr);
	}
	
	@RequestMapping(
			path = "/byList/{listId}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> getAllCustomersThatFollowsSongsListByListId(
			@PathVariable("listId") String listId,
			@RequestParam(name = "sortAttr", required = false, defaultValue = "id") String sortAttr,
			@RequestParam(name = "orderAttr", required = false, defaultValue = "ASC") String orderAttr){
		return this.songListsFollowService
				.getAllCustomersThatFollowsSongsListByListId(listId, sortAttr, orderAttr);
	}
	
	@RequestMapping(
			path = "/follow",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteAll() {
			return this.songListsFollowService
					.deleteAll();
	}
}
