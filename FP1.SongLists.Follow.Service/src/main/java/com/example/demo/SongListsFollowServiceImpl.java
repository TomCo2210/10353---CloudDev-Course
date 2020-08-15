package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SongListsFollowServiceImpl implements SongListsFollowService {

	private SongsListsServiceCrud songsListsServiceCrud;
	private CustomersServiceCrud customersServiceCrud;

	@Autowired
	public SongListsFollowServiceImpl(SongsListsServiceCrud songsListsServiceCrud,
			CustomersServiceCrud customersServiceCrud) {
		super();
		this.songsListsServiceCrud = songsListsServiceCrud;
		this.customersServiceCrud = customersServiceCrud;
	}

	@Override
	public Mono<Void> addSongsListToCustomerByCustomerEmail(String listId, Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> delteListByIdFromCustomerByCustomerEmail(String customerEmail, String listId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<CustomerAndSongsListPair> getByListAndCustomer(String customerEmail, String listId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<SongsList> getAllSongsListsByCustomerEmail(String customerEmail, String sortAttr, String orderAttr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Customer> getAllCustomersThatFollowsSongsListByListId(String listId, String sortAttr,
			String orderAttr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> deleteAll() {

		return this.songsListsServiceCrud
				.deleteAll()
				.and(
						this.customersServiceCrud
						.deleteAll());
	}

}
