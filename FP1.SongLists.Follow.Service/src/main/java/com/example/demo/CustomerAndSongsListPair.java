package com.example.demo;

public class CustomerAndSongsListPair {
	private CustomerDTO customer;
	private SongsListDTO songsList;

	public CustomerAndSongsListPair() {
		super();
	}

	public CustomerAndSongsListPair(Customer customer, SongsList songsList) {
		super();
		this.customer = new CustomerDTO(customer);
		this.songsList = new SongsListDTO(songsList);
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = new CustomerDTO(customer);
	}

	public SongsListDTO getSongsList() {
		return songsList;
	}

	public void setSongsList(SongsList songsList) {
		this.songsList = new SongsListDTO(songsList);
	}
}
