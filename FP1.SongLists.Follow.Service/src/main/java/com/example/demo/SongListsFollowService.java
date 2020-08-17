package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongListsFollowService {

	public Mono<Void> addSongsListToCustomerByCustomerEmail(String customerEmail, SongsList songsList);

	public Mono<Void> deleteListByIdFromCustomerByCustomerEmail(String customerEmail, String listId); 
	
	public Mono<CustomerAndSongsListPairDTO> getByListAndCustomer(String customerEmail, String listId);

	public Flux<SongsList> getAllSongsListsByCustomerEmail(String customerEmail, String sortAttr, String orderAttr);

	public Flux<Customer> getAllCustomersThatFollowsSongsListByListId(String listId, String sortAttr, String orderAttr);

	public Mono<Void> deleteAll();

}
