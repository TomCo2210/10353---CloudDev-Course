package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SongsLists")
public class SongsList {

	@Id
	private String id;
	private String name;
	private String userEmail;
	private Instant createdTimestamp;
	@DBRef
	private List<Song> listContent;
	//SONGSLISTID#SONGID
	private boolean deletionState;

	public SongsList() {
		listContent = new ArrayList<Song>();
	}

	public SongsList(String name, String userEmail, List<Song> listContent) {
		this.name = name;
		this.userEmail = userEmail;
		this.createdTimestamp = Instant.now();
		this.listContent = listContent;
		this.deletionState = false;
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

	public Instant getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Instant createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public List<Song> getListContent() {
		return listContent;
	}

	public void setListContent(List<Song> listContent) {
		this.listContent = listContent;
	}

	public SongsListDTO toDTO(SongsList entity) {
		SongsListDTO dto = new SongsListDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setUserEmail(entity.getUserEmail());
		DateFormat dateFormat = new SimpleDateFormat(GlobalVariables.DATE_FORMAT);
		dto.setCreatedTimestamp(dateFormat.format(entity.getCreatedTimestamp()));
		return dto;
	}

	public void setData(SongsList songsList) {
		if (songsList.name != null && songsList.name.trim().isEmpty())
			this.name = songsList.name;

		if (songsList.userEmail != null) {
			if (!Pattern.matches(GlobalVariables.EMAIL_PATTERN, songsList.userEmail.trim()))
				this.userEmail = songsList.userEmail.trim();
		}

		if (songsList.createdTimestamp != null) {

			this.createdTimestamp = songsList.createdTimestamp;
		}
	}

	public void addSongList(Song song) {
		if (listContent == null)
			listContent = new ArrayList<Song>();
		listContent.add(song);
	}

	public void removeSongById(String songId) {
		if (listContent == null)
			return;
		for (int i = 0; i < listContent.size(); i++)
			if (listContent.get(i).getSongId() == songId) {
				listContent.remove(i);
				return;
			}
	}

	public boolean getDeletionState() {
		return this.deletionState;
	}

	public void setDeletionState(boolean deletionState) {
		this.deletionState = deletionState;
	}
}
