package demo;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveDemoController {
	private ReactiveDemoService reactiveService;
	
	@Autowired
	public ReactiveDemoController(ReactiveDemoService reactiveService) {
		super();
		this.reactiveService = reactiveService;
	}

	@RequestMapping(
		path="/slowJob",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	// reactive project basic producer: Mono 
	public Mono<Demo> doSomeVeryLengthyWork(){
		System.err.println("doSomeVeryLengthyWork()");
		return Mono
				.delay(Duration.ofMillis(3000))
//				.delay(Duration.ofMinutes(10))
				.map(value->new Demo("hello reactive world"));
	}
	
	@RequestMapping(
			path="/demoes",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//				MediaType.APPLICATION_JSON_VALUE)// [{...}, {...}]
	public Flux<Demo> getManyItems() {
//		List<Demo> list = IntStream.range(1, 10+1)
//			.mapToObj(i->new Demo("message #" + i))
//			.collect(Collectors.toList());
		
//		return Flux.just(
//				list.get(0),
//				list.get(1),
//				list.get(2));
		
//		return Flux.fromIterable(list)
//				.delayElements(Duration.ofSeconds(10));
//				.delayElements(Duration.ofMillis(300));
		
//		return Flux.error(new RuntimeException("error right on the beginning of service handling"));

		// infinite flux
//		return Flux.interval(Duration.ofMillis(300))
//			.map(val->new Demo("another message (" + new Date() + ")"));
		
		// retrieve reactively from service 
		Flux<Dummy> dummies = this.reactiveService
			.getReactiveData();
		
		return dummies // Flux<Dummy>
				.map(Demo::new); // Flux<Demo>
				
	}
	
	@RequestMapping(path="/demoes",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteAll() {
//		return Mono.empty();
		
		// delete reactively from service
		return this.reactiveService
			.deleteAllData();
	}
	
	@RequestMapping(path="/demoes",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Demo> addNewDemo(@RequestBody Demo newDemo) {
		return this.reactiveService
			.storeNewDummy(newDemo.toDummy())
			.map(Demo::new);
	}

	@RequestMapping(path="/demoes/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Void> updateDemo(
			@PathVariable("id") String id,
			@RequestBody Demo updatedDemo) {
		return this.reactiveService
			.updateData(id, updatedDemo.getMessage());
	}

}

