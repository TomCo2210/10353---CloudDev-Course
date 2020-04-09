package songs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SongsServiceKeyValueStoreConsumer implements SongsService{
	private RestTemplate restTemplate;
	
	private String url;
	
	public SongsServiceKeyValueStoreConsumer() {
		this.restTemplate = new RestTemplate();
	}
	
	@Value("${storage.service.url}")
	public void setUrl(String url) {
		this.url = url;
		System.err.println("****** " + this.url);
	}
	
	@Override
	public Song create(Song song) {
		song.setSongId(null);
		// consuming other service method using POST
		KeyObjectPair keyObject = this.restTemplate
			.postForObject(
					this.url, 
					song, 
					KeyObjectPair.class);
		Song newObject = keyObject.getObject();
		newObject.setSongId(keyObject.getKey());
		updateSong(keyObject.getKey(), newObject);
		return newObject;
	}

	@Override
	public Song getSongById(String key) {
		// consuming other service method using GET
		return this.restTemplate
			.getForObject(
					this.url + "/{id}", 
					Song.class, 
					key);
	}

	@Override
	public Song updateSong(String id, Song update) {
		// consuming other service method using PUT
		this.restTemplate
			.put(
				this.url + "/{id}", 
				update, 
				id);
		
		return getSongById(id);
	}

	@Override
	public void deleteAllSongs() {
		// consuming other service method using DELETE
		this.restTemplate
			.delete(this.url);
		
	}

}
