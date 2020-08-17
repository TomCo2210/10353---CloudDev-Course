package com.example.demo;

import java.util.Comparator;

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
	public Mono<Void> addSongsListToCustomerByCustomerEmail(String customerEmail, SongsList songsList) {
		return this.songsListsServiceCrud.save(songsList).flatMap(newSongsList -> { 
			return this.customersServiceCrud.findByCustomerEmail(customerEmail).flatMap(oldCustomer ->{ 
				oldCustomer.addSongsList(newSongsList);
				return this.customersServiceCrud.save(oldCustomer);
			});
		} ).flatMap(d -> Mono.empty());
	}

	@Override
	public Mono<Void> deleteListByIdFromCustomerByCustomerEmail(String customerEmail, String listId) {
		return this.customersServiceCrud.findByCustomerEmail(customerEmail) // Mono<SongLists>
				.flatMap(oldCustomer -> {
					SongsList songsList = oldCustomer.removeSongsListById(listId);
					if (songsList != null)
						this.songsListsServiceCrud.delete(songsList);
					return this.customersServiceCrud.save(oldCustomer);
				})// Mono<SongLists>
				.flatMap(d -> Mono.empty());// Mono<Void>
	}

	@Override
	public Mono<CustomerAndSongsListPairDTO> getByListAndCustomer(String customerEmail, String listId) {
		return this.customersServiceCrud.findByCustomerEmail(customerEmail).flatMap(customer -> {
			if (customer == null)
				return Mono.empty();
			return Mono.empty();
//TODO: fix

		});
	}

	@Override
	public Flux<SongsList> getAllSongsListsByCustomerEmail(String customerEmail, String sortAttr, String orderAttr) {
		return this.customersServiceCrud.findByCustomerEmail(customerEmail).flatMapMany(customer -> {
			if (customer == null)
				return Flux.empty();
			return Flux.fromIterable(customer.getSongsLists()).sort(new Comparator<SongsList>() {
				@Override
				public int compare(SongsList o1, SongsList o2) {
					return orderAttr.equalsIgnoreCase("DESC") ? o2.getId().compareTo(o1.getId())
							: o1.getId().compareTo(o2.getId());
				}
			});
		});
	}

	@Override
	public Flux<Customer> getAllCustomersThatFollowsSongsListByListId(String listId, String sortAttr,
			String orderAttr) {
		return null;
	}

	@Override
	public Mono<Void> deleteAll() {
		return this.songsListsServiceCrud.deleteAll().and(this.customersServiceCrud.deleteAll());
	}
}
