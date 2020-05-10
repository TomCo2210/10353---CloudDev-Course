package storage;

import java.util.Arrays;
import java.util.Map;

public class Song {
	String songId;
	String name;
	int publishedYear;
	String lyrics;
	String performer;
	String producer;
	String[] genres;
	Map<String, String> authors;

	public Song() {
	}

	public Song(String songId, String name, int publishedYear, String lyrics, String performer, String producer,
			String[] genres, Map<String, String> authors) {
		super();
		this.songId = songId;
		this.name = name;
		this.publishedYear = publishedYear;
		this.lyrics = lyrics;
		this.performer = performer;
		this.producer = producer;
		this.genres = genres;
		this.authors = authors;
	}

	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String[] getGenres() {
		return genres;
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	public Map<String, String> getAuthors() {
		return authors;
	}

	public void setAuthors(Map<String, String> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "Song [songId=" + songId + ", name=" + name + ", publishedYear=" + publishedYear + ", lyrics=" + lyrics
				+ ", performer=" + performer + ", producer=" + producer + ", genres=" + Arrays.toString(genres)
				+ ", authors=" + authors + "]";
	}


}
