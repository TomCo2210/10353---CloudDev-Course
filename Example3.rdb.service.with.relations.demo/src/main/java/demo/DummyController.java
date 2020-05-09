package demo;

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
public class DummyController {
	private DummyService dummies;
	
	@Autowired
	public DummyController(DummyService dummies) {
		this.dummies = dummies;
	}

	@RequestMapping(path = "/dummies",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary create (@RequestBody DummyBoundary dummy) {
		return new DummyBoundary(
				this.dummies
					.create(dummy.toEntity()));
	}
	
	
	// Read All - SELECT 
	@RequestMapping(path = "/dummies",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary[] getAll(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return 
		  this.dummies
			.getAll(size, page) // List<Dummy>
			.stream() // Stream<Dummy>
			.map(DummyBoundary::new)// Stream<DummyBoundary>
			.collect(Collectors.toList()) // List<DummyBoundary>
			.toArray(new DummyBoundary[0]); // DummyBoundary[]
	}
	
	// Read One Item - SELECT
	@RequestMapping(path = "/dummies/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary getDummyById (@PathVariable("id") Long id) {
		return new DummyBoundary(
				this.dummies
				.getDummyById(id));
	}
	
	// Update - UPDATE
	@RequestMapping(path = "/dummies/{idToUpdate}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update (@PathVariable("idToUpdate") String idToUpdate, 
			@RequestBody DummyBoundary update) {
		this.dummies
			.update(Long.parseLong(idToUpdate), update.toEntity());
	}
	
	// Delete - DELETE
	@RequestMapping(path = "/dummies",
			method = RequestMethod.DELETE)
	public void deleteAll () {
		this.dummies
			.deleteAll();
	}

	@RequestMapping(path = "/dummmies/search/byIdRange/{min}/{max}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary[] getDummiesByIdInRange (
			@PathVariable("min") long min,
			@PathVariable("max") long max,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return this.dummies
				.getDummiesByIdRange(min, max, size, page)
				.stream()
				.map(DummyBoundary::new)
				.collect(Collectors.toList())
				.toArray(new DummyBoundary[0]);
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleException(DummyNotFoundExcetion e){
		return Collections
				.singletonMap("error", (e.getMessage() != null)?e.getMessage():"Dummy not found");
	}
}



