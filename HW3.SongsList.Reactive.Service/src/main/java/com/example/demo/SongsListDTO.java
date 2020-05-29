package com.example.demo;

import java.time.Instant;

public class SongsListDTO {
	private String id;
	private String name;
	private String userEmail;
	private String createdTimestamp;

	public SongsListDTO() {

	}

	public SongsListDTO(String id, String name, String userEmail, String createdTimestamp) {
		super();
		this.id = id;
		this.name = name;
		this.userEmail = userEmail;
		this.createdTimestamp = createdTimestamp;
	}
	
	
	public SongsListDTO(SongsList songsList) {
		super();
		this.id = songsList.getId();
		this.name = songsList.getName();
		this.userEmail = songsList.getUserEmail();
		this.createdTimestamp = songsList.getCreatedTimestamp().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public SongsList toEntity() {
		SongsList entity = new SongsList();
		entity.setId(this.getId());
		entity.setName(this.getName());
		entity.setUserEmail(this.getUserEmail());
		entity.setCreatedTimestamp(Instant.now());

		return entity;
	}
}
