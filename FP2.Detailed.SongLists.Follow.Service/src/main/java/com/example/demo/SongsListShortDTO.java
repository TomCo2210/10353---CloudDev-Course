package com.example.demo;

public class SongsListShortDTO {

	private String id;
	
	private String songsListId;

	public SongsListShortDTO() {	}

	public SongsListShortDTO(String songsListId) {
		super();
		this.songsListId = songsListId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSongsListId() {
		return songsListId;
	}

	public void setSongsListId(String songsListId) {
		this.songsListId = songsListId;
	}
}
