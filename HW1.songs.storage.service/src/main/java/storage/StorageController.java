package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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
public class StorageController {
	private Map<String, Song> storage;
	private AtomicLong idGenerator;

	@PostConstruct
	public void init() {
		this.idGenerator = new AtomicLong(1);
		storage = Collections.synchronizedMap(new TreeMap<String, Song>());
	}

	/*
	 * input: { "key":"value", "key2":true, "key3":12, "key4":12.0, "key5":{ } }
	 */
	@RequestMapping(path = "/storage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Song store(@RequestBody Song song) {
		// stub implementation
		if (song.songId == null || song.songId.isEmpty())
			throw new DataIncompleteException("Data Incomplete! Missing Data Provided!");
		if (storage.containsKey(song.songId))
			throw new IdAlreadyExistException("Id Already Exists: " + song.songId);
		storage.put(song.songId, song);
		return song;
	}

	@RequestMapping(path = "/storage/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@PathVariable("id") String id, @RequestBody Song song) {
		if (!song.getSongId().equals(id))
			throw new CannotChangeIdException("Cannot Change Id");
		if (!storage.containsKey(id))
			throw new DataNotFoundException("could not find item by key: " + id);
		storage.put(id, song);
	}

	@RequestMapping(path = "/storage/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Song get(@PathVariable("id") String key) {
		if (!storage.containsKey(key))
			throw new DataNotFoundException("could not find item by key: " + key);
		return storage.get(key);
	}

	@RequestMapping(path = "/storage/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] getAllUsingPagination(@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "songId") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order) {

		int startPoint = size * page;

		List<Song> list = new ArrayList<>(this.storage.values());
		Collections.sort(list, new Comparator<Song>() {
			@Override
			public int compare(Song first, Song second) {
				switch (sortAttribute) {
				case "songId":
					return first.songId.compareTo(second.songId);
				case "name":
					return first.name.compareTo(second.name);
				case "publishedYear":
					return (first.publishedYear + "").compareTo(second.publishedYear + "");
				case "lyrics":
					return first.lyrics.compareTo(second.lyrics);
				case "performer":
					return first.performer.compareTo(second.performer);
				case "producer":
					return first.producer.compareTo(second.producer);
				default:
					return 0;
				}
			}
		});
		if (!order.equals("asc")) {
			Collections.reverse(list);
		}
		return list.stream().skip(startPoint).limit(size).collect(Collectors.toList())
				.toArray((Object[]) new Object[0]);
	}

	@RequestMapping(path = "/storage/name",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] getAllByNameUsingPagination(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "songId") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order,
			@RequestParam(name = "criteriaValue", required = true) String value) {

		int startPoint = size * page;

		List<Song> list = new ArrayList<>(this.storage.values());
		Collections.sort(list, new Comparator<Song>() {
			@Override
			public int compare(Song first, Song second) {
				switch (sortAttribute) {
				case "songId":
					return first.songId.compareTo(second.songId);
				case "name":
					return first.name.compareTo(second.name);
				case "publishedYear":
					return (first.publishedYear + "").compareTo(second.publishedYear + "");
				case "lyrics":
					return first.lyrics.compareTo(second.lyrics);
				case "performer":
					return first.performer.compareTo(second.performer);
				case "producer":
					return first.producer.compareTo(second.producer);
				default:
					return 0;
				}
			}
		});
		if (!order.equals("asc")) {
			Collections.reverse(list);
		}
		return list.stream().filter(s -> s.name.equals(value)).skip(startPoint).limit(size).collect(Collectors.toList())
				.toArray((Object[]) new Object[0]);
	}

	@RequestMapping(path = "/storage/performer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] getAllByPerformerUsingPagination(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "songId") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String order,
			@RequestParam(name = "criteriaValue", required = true) String value) {

		int startPoint = size * page;

		List<Song> list = new ArrayList<>(this.storage.values());
		Collections.sort(list, new Comparator<Song>() {
			@Override
			public int compare(Song first, Song second) {
				switch (sortAttribute) {
				case "songId":
					return first.songId.compareTo(second.songId);
				case "name":
					return first.name.compareTo(second.name);
				case "publishedYear":
					return (first.publishedYear + "").compareTo(second.publishedYear + "");
				case "lyrics":
					return first.lyrics.compareTo(second.lyrics);
				case "performer":
					return first.performer.compareTo(second.performer);
				case "producer":
					return first.producer.compareTo(second.producer);
				default:
					return 0;
				}
			}
		});
		if (!order.equals("asc")) {
			Collections.reverse(list);
		}
		return list.stream().filter(s-> s.performer.equals(value)).skip(startPoint).limit(size).collect(Collectors.toList())
				.toArray((Object[]) new Object[0]);
	}

	@RequestMapping(path = "/storage", method = RequestMethod.DELETE)
	public void clearAllData() {
		storage.clear();
	}

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

	@RequestMapping(
			// storage?size={size}&page={page}
			path = "/storage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] getAllUsingPagination(@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.storage.values().stream().skip(size * page).limit(size).collect(Collectors.toList())
				.toArray((Object[]) new Object[0]);
	}

}
