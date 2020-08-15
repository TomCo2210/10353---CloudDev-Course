package com.example.demo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SongsLists")
public class SongsList {

	@Id
	private String id;
	
	public SongsList() { }
	
	public SongsList(String id) {
		super();
		this.setId(id);
	}
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
