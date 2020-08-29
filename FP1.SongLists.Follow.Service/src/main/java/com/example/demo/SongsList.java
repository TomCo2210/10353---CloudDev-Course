package com.example.demo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SongsLists")
public class SongsList {

	@Id
	private String id;
	
	private String songsListId;
	
	public SongsList() { }
	
	public SongsList(String songListId) {
		super();
		this.setSongsListId(songListId);
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

	public void setSongsListId(String songListId) {
		this.songsListId = songListId;
	}
	
	public SongsListDTO toDTO() {
		return new SongsListDTO(songsListId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SongsList other = (SongsList) obj;
		if (songsListId == null) {
			if (other.songsListId != null)
				return false;
		} else if (!songsListId.equals(other.songsListId))
			return false;
		return true;
	}

	
	
}
