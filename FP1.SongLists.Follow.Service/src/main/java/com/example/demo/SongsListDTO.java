package com.example.demo;

public class SongsListDTO {

	private String id;
	
	public SongsListDTO() { }
	
	public SongsListDTO(String id) {
		super();
		this.setId(id);
	}
	
	public SongsListDTO(SongsList songsList) {
		super();
		this.setId(songsList.getId());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SongsList toEntity() {
		SongsList entity = new SongsList();
		entity.setId(this.getId());
		return entity;
	}

}
