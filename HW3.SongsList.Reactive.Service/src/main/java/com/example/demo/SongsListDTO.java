package com.example.demo;

import java.util.Date;

public class SongsListDTO {
	private String id;
	private String name;
	private String userEmail;
	private Date createdTimestamp;
	
	public SongsListDTO() {
		
	}
	
	public SongsListDTO(String id, String name, String userEmail, Date createdTimestamp) {
		super();
		this.id = id;
		this.name = name;
		this.userEmail = userEmail;
		this.createdTimestamp = createdTimestamp;
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
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	
	public SongsList toEntity(SongsListDTO dto) {
		SongsList entity = new SongsList();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setUserEmail(dto.getUserEmail());
		entity.setCreatedTimestamp(dto.getCreatedTimestamp());
		return entity;
	}
}
