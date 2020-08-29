package com.example.demo;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SongListsFollowServiceImpl implements SongListsFollowService {

	private SongsListsServiceCrud songsListsServiceCrud;
	private CustomersServiceCrud customersServiceCrud;

	@Autowired
	public SongListsFollowServiceImpl(
			SongsListsServiceCrud songsListsServiceCrud,
			CustomersServiceCrud customersServiceCrud) {
		super();
		this.songsListsServiceCrud = songsListsServiceCrud;
		this.customersServiceCrud = customersServiceCrud;
	}

	@Override
	public Mono<Void> addSongsListToCustomerByCustomerEmail(String customerEmail, SongsList songsList) {
		return this.songsListsServiceCrud.findBySongsListId(songsList.getSongsListId())
				.defaultIfEmpty(songsList)
				.flatMap(newSongsList -> { 
			return this.customersServiceCrud.findByCustomerEmail(customerEmail)
					.defaultIfEmpty(new Customer(customerEmail))
					.flatMap(oldCustomer ->{
						if (newSongsList.getId() == null || newSongsList.getId().isEmpty())
							this.songsListsServiceCrud.save(newSongsList).subscribe();
				oldCustomer.addSongsList(newSongsList);
				return this.customersServiceCrud.save(oldCustomer);
			});
		} ).flatMap(d -> Mono.empty());
	}

	@Override
	public Mono<Void> deleteListByIdFromCustomerByCustomerEmail(String customerEmail, String listId) {
		return this.customersServiceCrud.findByCustomerEmail(customerEmail)
				.flatMap(oldCustomer -> {
					SongsList songsList = oldCustomer.removeSongsListById(listId);
					if (songsList != null)
						this.songsListsServiceCrud.delete(songsList);
					return this.customersServiceCrud.save(oldCustomer);
				})
				.flatMap(d -> Mono.empty());
	}

	@Override
	public Mono<CustomerAndSongsListPair> getByListAndCustomer(String customerEmail, String listId) {
		return this.customersServiceCrud.findByCustomerEmail(customerEmail).defaultIfEmpty(new Customer()).flatMap(customer -> {
			if (customer.getCustomerEmail() == null)
				return Mono.empty();
			return this.songsListsServiceCrud.findBySongsListId(listId).defaultIfEmpty(new SongsList()).flatMap(songsList -> {
				if (songsList.getSongsListId() == null)
					return Mono.empty();
				if (customer.getSongsLists().contains(new SongsList(listId))) {
					return Mono.just(new CustomerAndSongsListPair(customer, songsList));
				}
				return Mono.empty();
			});
		
		});
	}

	@Override
	public Flux<SongsList> getAllSongsListsByCustomerEmail(String customerEmail, String sortAttr, String orderAttr) {
		return this.customersServiceCrud
				.findByCustomerEmail(customerEmail)
				.flatMapMany(customer -> {
						if (customer == null)
							return Flux.empty();
						return Flux.fromIterable(customer.getSongsLists()).sort(new Comparator<SongsList>() {
							@Override
							public int compare(SongsList o1, SongsList o2) {
								return orderAttr.equalsIgnoreCase("DESC") ? o2.getSongsListId().compareTo(o1.getSongsListId())
										: o1.getSongsListId().compareTo(o2.getSongsListId());
							}
						});
				});
	}

	@Override
	public Flux<Customer> getAllCustomersThatFollowsSongsListBySongsListId(String listId, String sortAttr,
			String orderAttr) {
		return this.customersServiceCrud.findAllBySongsListsSongsListId(listId).sort(new Comparator<Customer>() {
			@Override
			public int compare(Customer o1, Customer o2) {
				return orderAttr.equalsIgnoreCase("DESC") ? o2.getCustomerEmail().compareTo(o1.getCustomerEmail())
						: o1.getCustomerEmail().compareTo(o2.getCustomerEmail());
			}
		});
	}

	@Override
	public Mono<Void> deleteAll() {
		return this.songsListsServiceCrud.deleteAll().and(this.customersServiceCrud.deleteAll());
	}
}
