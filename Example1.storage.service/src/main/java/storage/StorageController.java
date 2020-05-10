package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
	private Map<String, Map<String, Object>> storage;
	private AtomicLong idGenerator;

	@PostConstruct
	public void init() {
		this.idGenerator = new AtomicLong(1);
		storage = Collections.synchronizedMap(new TreeMap<String, Map<String, Object>>());
	}

	/*
	 * input: { "key":"value", "key2":true, "key3":12, "key4":12.0, "key5":{ } }
	 */
	@RequestMapping(path = "/storage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectWithKey store(@RequestBody Map<String, Object> object) {
		// stub implementation
		String newKey = "" + this.idGenerator.getAndIncrement();
		ObjectWithKey rv = new ObjectWithKey(newKey, object);
		storage.put(newKey, object);
		return rv;
	}

	@RequestMapping(path = "/storage/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@PathVariable("id") String id, @RequestBody Map<String, Object> object) {
		storage.put(id, object);
	}

	@RequestMapping(path = "/storage/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> get(@PathVariable("id") String key) {
		if (storage.get(key) != null)
			return storage.get(key);
		else
			throw new DataNotFoundException("could not find item by key: " + key);
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

	/*
	 * Assignment: Add suppport in the storage service for the following request GET
	 * /storage?size={size}&page={page}
	 */

	@RequestMapping(
			// storage?size={size}&page={page}
			path = "/storage",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>[] getAllUsingPagination(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {

		int startPoint = size * page;
		return this.storage
				.values()
				.stream()
				.skip(startPoint)
				.limit(size)
				.collect(Collectors.toList())
				.toArray((Map<String, Object>[]) new Map[0]);
//		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
//		while (startPoint < Integer.parseInt(this.idGenerator.toString()) && mapList.size() < size) {
//			try {
//				Map<String, Object> m = get(startPoint + "");
//				mapList.add(m);
//			} catch (Exception e) {
//				startPoint++;
//			}
//		}
//		return mapList.toArray(new Map<String, Object>[0]);
	}
	
	
}
