package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongListsFollowService {

	public Mono<Void> addSongsListToCustomerByCustomerEmail(String listId, Object entity);

	public Mono<Void> delteListByIdFromCustomerByCustomerEmail(String customerEmail, String listId); 
	
	public Mono<CustomerAndSongsListPair> getByListAndCustomer(String customerEmail, String listId);

	public Flux<SongsList> getAllSongsListsByCustomerEmail(String customerEmail, String sortAttr, String orderAttr);

	public Flux<Customer> getAllCustomersThatFollowsSongsListByListId(String listId, String sortAttr, String orderAttr);

	public Mono<Void> deleteAll();

}
