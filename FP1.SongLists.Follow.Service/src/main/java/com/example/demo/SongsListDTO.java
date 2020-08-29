package com.example.demo;

public class SongsListDTO {

	private String songsListId;

	public SongsListDTO() {	}

	public SongsListDTO(String songsListId) {
		super();
		this.songsListId = songsListId;
	}

	public SongsListDTO(SongsList songsList) {
		super();
		this.songsListId = songsList.getSongsListId();
	}
	
	public String getSongsListId() {
		return songsListId;
	}

	public void setSongsListId(String songsListId) {
		this.songsListId = songsListId;
	}

	public SongsList toEntity() {
		return new SongsList(this.songsListId);
	}

	

}
