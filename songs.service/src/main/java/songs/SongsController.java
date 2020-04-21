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

	@RequestMapping(path = "/songs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Song createSong(@RequestBody Song song) {
		return this.songs.create(song);
	}

	@RequestMapping(path = "/song/{songId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Song getSongById(@PathVariable("songId") String songId) throws DataNotFoundException {
		return this.songs.getSongById(songId);
	}

	@RequestMapping(path = "/Songs/{songId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@PathVariable("songId") String songId, @RequestBody Song update) {
			this.songs.updateSong(songId, update);
		
		// TODO: cannot change songId!
	}

	@RequestMapping(path = "/songs", method = RequestMethod.DELETE)
	public void deleteAll() {
		this.songs.deleteAllSongs();
	}

	// GET
	// /songs/search?size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}

	@RequestMapping(
			path = "/songs/All",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] getAllUsingPagination(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "songId") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order) {

		return this.songs
				.getAllSongs(size,page,sortAttribute,order);
	}

	// GET
	// /songs/search?criteriaType=byName&criteriaValue={value}&size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}
	@RequestMapping(
			path = "/songs/search",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE,
			params = {"criteriaType=byName"})
	public Object[] getSpecifiedNameWithPagination(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "songId") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order,
			@RequestParam(name = "criteriaType", required = false) String criteriaType,
			@RequestParam(name = "criteriaValue", required = true) String value) {

		return this.songs
				.getAllSongsByName(size,page,sortAttribute,order,value);
		// TODO: search by name.
	}

	// GET
	// /songs/search?criteriaType=byPerformer&criteriaValue={value}&size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}
	@RequestMapping(
			path = "/songs/search",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE,
			params = {"criteriaType=byPerformer"})
	public Object[] getSpecifiedPerformerWithPagination(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "songId") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order,
//			@RequestParam(name = "criteriaType", required = false) String criteriaType,
			@RequestParam(name = "criteriaValue", required = true) String value) {

		return this.songs
				.getAllSongsByPerformer(size,page,sortAttribute,order,value);
		// TODO: search by Performer.
	}

//	// GET
//	// /songs/search?criteriaType=byGenre&criteriaValue={value}&size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}
//	@RequestMapping(
//			path = "/storage/search",
//			method = RequestMethod.GET,
//			produces = MediaType.APPLICATION_JSON_VALUE,
//			params = {"criteriaType=byGenre"})
//	public Map<String, Object>[] getSpecifiedGenreWithPagination(
//			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
//			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
//			@RequestParam(name = "sortBy", required = false, defaultValue = "songId") String sortAttribute,
//			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order,
//			@RequestParam(name = "criteriaValue", required = true) int value) {
//
//		return this.songs
//				.getAllSongsByGenre(size,page,sortAttribute,order,value);
//		// TODO: search by genre.
//	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleError(DataNotFoundException e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Item not found";
		}

		return Collections.singletonMap("error", message);
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public Map<String, Object> handleError(CannotChangeIdException e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Cannot Change Id";
		}

		return Collections.singletonMap("error", message);
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleError(DataIncompleteException e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Data Incomplete! Missing Data Provided!";
		}

		return Collections.singletonMap("error", message);
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleError(IdAlreadyExistException e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Id Already Exists! Cannot Overwrite Data";
		}

		return Collections.singletonMap("error", message);
	}
	
	
}
