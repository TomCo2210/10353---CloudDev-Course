package com.example.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SongsLists")
public class SongsList {

	private String id;
	private String name;
	private String userEmail;
	private Date createdTimestamp;
	private List<Song> listContent;

	public SongsList() {
		listContent = new ArrayList<Song>();
	}

	public SongsList(String name, String userEmail, List<Song> listContent) {
		this.name = name;
		this.userEmail = userEmail;
		Date date = new Date(System.currentTimeMillis()); // this object contains the current date value
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		try {
			this.createdTimestamp = formatter.parse(formatter.format(date));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		this.listContent = listContent;
	}
	@Id
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
		if(songsList.name != null && songsList.name.trim().isEmpty())
			this.name = songsList.name;
		
		if(songsList.userEmail != null) {
			if (!Pattern.matches(GlobalVariables.EMAIL_PATTERN, songsList.userEmail.trim()))
				this.userEmail = songsList.userEmail.trim();
		}
		
		if(songsList.createdTimestamp != null) {
			
			this.createdTimestamp = songsList.createdTimestamp;		
		}
	}

	public void addSongList(Song song) {
		if(listContent == null)
			listContent = new ArrayList<Song>();
		listContent.add(song);	
	}

	public void removeSongById(String songId) {
		if(listContent == null)
			return;
		for(int i =0;i<listContent.size();i++)
			if(listContent.get(i).getSongId() == songId) {
				listContent.remove(i);
				return;
			}	
	}
}
