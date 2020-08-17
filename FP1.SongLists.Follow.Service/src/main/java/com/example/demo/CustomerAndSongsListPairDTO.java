package com.example.demo;

public class CustomerAndSongsListPairDTO {
	private Customer customer;
	private SongsList songsList;

	public CustomerAndSongsListPairDTO() {
		super();
	}

	public CustomerAndSongsListPairDTO(Customer customer, SongsList songsList) {
		super();
		this.customer = customer;
		this.songsList = songsList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SongsList getSongsList() {
		return songsList;
	}

	public void setSongsList(SongsList songsList) {
		this.songsList = songsList;
	}
}
