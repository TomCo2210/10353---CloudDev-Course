package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ReactiveDetailedSongListsFollowController {

	private DetailedSongListsFollowService detailedSongListsFollowService;

	@Autowired
	public ReactiveDetailedSongListsFollowController(DetailedSongListsFollowService detailedSongListsFollowService) {
		super();
		this.detailedSongListsFollowService = detailedSongListsFollowService;
	}

	@RequestMapping(
			path = "/byCustomer/{customerEmail}/details",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsListDTO> getSongsListsDetailsByCustomer(
			@PathVariable("customerEmail") String customerEmail) {
		return this.detailedSongListsFollowService.getSongsListsDetailsByCustomer(customerEmail);
	}

	@RequestMapping(
			path = "/byListAndCustomer/{customerEmail}/songs",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<SongsListAndSongPair> getAllSongsByCustomer(
			@PathVariable("customerEmail") String customerEmail) {
		return this.detailedSongListsFollowService.getAllSongsByCustomer(customerEmail);
	}
}
