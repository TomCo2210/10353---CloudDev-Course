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
	public Flux<Void> getSongsListsDetailsByCustomer(
			@PathVariable("customerEmail") String customerEmail) {
		return Flux.empty();
	}

	@RequestMapping(
			path = "/byListAndCustomer/{customerEmail}/songs",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Void> getAllSongsByCustomer(
			@PathVariable("customerEmail") String customerEmail) {
		return Flux.empty();
	}

}
