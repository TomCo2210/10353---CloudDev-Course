package songs;

import java.util.Map;

public interface SongsService {

	public Song create(Song song);

	public Song getSongById(String id);

	public Song updateSong(String id, Song update);

	public void deleteAllSongs();

	public Object[] getAllSongs(int size, int page, String sortAttribute, String order);

	public Object[] getAllSongsByPerformer(int size, int page, String sortAttribute, String order,
			String value);

	public Object[] getAllSongsByName(int size, int page, String sortAttribute, String order, String value);
	
}
