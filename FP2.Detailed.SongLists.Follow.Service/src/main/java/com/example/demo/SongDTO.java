package com.example.demo;

public class SongDTO {

	private String songId;

	
	public SongDTO() {
	}

	public SongDTO(String songId) {
		super();
		this.songId = songId;
	}
	
	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}
	
	
}
