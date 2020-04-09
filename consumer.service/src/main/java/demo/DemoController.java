package demo;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	private DemoService demoes;
	
	@Autowired
	public DemoController(DemoService demoes) {
		this.demoes = demoes;
	}
	
	@RequestMapping(
			path = "/demo",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Demo createDemo (@RequestBody Demo demo){
		return this.demoes
				.create(demo);
	}
	
	@RequestMapping(
			path = "/demo/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Demo getDemoById (@PathVariable("id") String id) throws DemoNotFoundException {
		return this.demoes
				.getDemoById (id);
//		if (!id.startsWith("-")) {
//			return new Demo(id, "stub", 9);
//		}else {
//			throw new DemoNotFoundException("could not find demo for id: " + id);
//		}
	}
	
	@RequestMapping(
			path = "/demo/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update (
			@PathVariable("id") String id, 
			@RequestBody Demo update) {
		//Demo existing = getDemoById(id);
		// update data structure
		this.demoes
			.updateDemo(id, update);
	}
	
	@RequestMapping(
			path = "/demo",
			method = RequestMethod.DELETE)
	public void deleteAll () {
		this.demoes
			.deleteAllDemoes();
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleError (DemoNotFoundException e){
		String message = e.getMessage();
		if (message == null) {
			message = "Demo not found";
		}
		
		return Collections.singletonMap("error", message);
	}
}


