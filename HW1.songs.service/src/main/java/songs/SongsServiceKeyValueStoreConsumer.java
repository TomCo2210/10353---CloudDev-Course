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
		  restTemplate.setErrorHandler(new ErrorHandlerRESTHelper());
	}
	
	@Value("${storage.service.url}")
	public void setUrl(String url) {
		this.url = url;
		System.err.println("****** " + this.url);
	}
	
	@Override
	public Song create(Song song) {
		// consuming other service method using POST
		Song keyObject = this.restTemplate
			.postForObject(
					this.url, 
					song,
					Song.class);
		
		return keyObject;
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

	@Override
	public Object[] getAllSongs(int size, int page, String sortAttribute, String order) {
		// TODO Auto-generated method stub
		return this.restTemplate
				.getForObject(
						this.url + "/all?size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}",
						Song[].class, 
						size,
						page,
						sortAttribute,
						order);
	}

	@Override
	public Object[] getAllSongsByPerformer(int size, int page, String sortAttribute, String order, String value) {
		return this.restTemplate
				.getForObject(
						this.url + "/performer?criteriaValue={value}&size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}",
						Song[].class, 
						value,
						size,
						page,
						sortAttribute,
						order);
	}

	@Override
	public Object[] getAllSongsByName(int size, int page, String sortAttribute, String order, String value) {
		return this.restTemplate
				.getForObject(
						this.url + "/name?criteriaValue={value}&size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}",
						Song[].class, 
						value,
						size,
						page,
						sortAttribute,
						order);
	}
	

	
	

}
