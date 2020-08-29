package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customers")
public class Customer {
	@Id
	private String customerEmail;
	private List<SongsList> songsLists;

	public Customer() {
		this.songsLists = new ArrayList<SongsList>();
	}

	public Customer(String customerEmail) {
		super();
		this.customerEmail = customerEmail;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<SongsList> getSongsLists() {
		return songsLists;
	}

	public void setSongsLists(List<SongsList> songsLists) {
		this.songsLists = songsLists;
	}

	public SongsList removeSongsListById(String listId) {
		if (songsLists == null)
			return null;
		for (int i = 0; i < songsLists.size(); i++)
			if (songsLists.get(i).getSongsListId().equals(listId)) {
				SongsList songsList = songsLists.get(i);
				songsLists.remove(songsList);
				return songsList;
			}
		return null;
	}

	public void addSongsList(SongsList newSongsList) {
		if (songsLists == null)
			songsLists = new ArrayList<SongsList>();
		if(newSongsList == null)
			throw new BadDataException("Invalid SongsList");
		if(newSongsList.getSongsListId().trim().isEmpty())
			throw new BadDataException("Invalid SongsListId");
		songsLists.add(newSongsList);
	}

}