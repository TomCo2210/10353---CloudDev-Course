package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SongsList {

	private String id;
	private String name;
	private String userEmail;
	private Date createdTimestamp;
	private Map<String, String> listContent;

	public SongsList() {
		listContent = new HashMap<String, String>();
	}

	public SongsList(String name, String userEmail, Map<String, String> listContent) {
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

	public Map<String, String> getListContent() {
		return listContent;
	}

	public void setListContent(Map<String, String> listContent) {
		this.listContent = listContent;
	}
	
	public SongsListDTO toDTO(SongsList entity) {
		SongsListDTO dto = new SongsListDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setUserEmail(entity.getUserEmail());
		dto.setCreatedTimestamp(entity.getCreatedTimestamp());
		return dto;
	}
}
