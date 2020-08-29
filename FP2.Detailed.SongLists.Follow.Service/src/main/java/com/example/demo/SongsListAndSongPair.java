package com.example.demo;

public class SongsListAndSongPair {

	private String listId;
	private String songId;

	public SongsListAndSongPair() {
	}

	public SongsListAndSongPair(String listId, String songId) {
		super();
		this.listId = listId;
		this.songId = songId;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}

}
