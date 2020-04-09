package songs;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SongsController {
	private SongsService songs;

	@Autowired
	public SongsController(SongsService songs) {
		this.songs = songs;
	}
	
	@RequestMapping(
			path = "/songs",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Song createSong (@RequestBody Song song){
		return this.songs
				.create(song);
		// TODO: if songId already exist return code 500
	}

	@RequestMapping(
			path = "/song/{songId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Song getSongById (@PathVariable("songId") String songId) throws SongNotFoundException {
		return this.songs
				.getSongById (songId);
	}



	@RequestMapping(
			path = "/Songs/{songId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update (
			@PathVariable("songId") String songId, 
			@RequestBody Song update) {
		this.songs
			.updateSong(songId, update);
		//TODO: 404 if song not found
		//TODO: cannot change songId!
	}
	
	@RequestMapping(
			path = "/songs",
			method = RequestMethod.DELETE)
	public void deleteAll () {
		this.songs
			.deleteAllSongs();
	}

	


	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleError (SongNotFoundException e){
		String message = e.getMessage();
		if (message == null) {
			message = "Song not found";
		}
		
		return Collections.singletonMap("error", message);
	}
}
