package com.example.demo;

public class SongDTO {

	private String songId;

	
	public SongDTO() {
	}

	public SongDTO(String songId) {
		super();
		this.songId = songId;
	}
	
	public SongDTO(Song song) {
		songId = song.getSongId();
	}
	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}
	
	public Song toEntity()
	{
		Song song = new Song();
		song.setSongId(this.songId);
		return song;
	}
}
