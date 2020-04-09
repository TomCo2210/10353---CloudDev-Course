package songs;

public interface SongsService {

	public Song create(Song song);

	public Song getSongById(String id);

	public Song updateSong(String id, Song update);

	public void deleteAllSongs();
	
}
