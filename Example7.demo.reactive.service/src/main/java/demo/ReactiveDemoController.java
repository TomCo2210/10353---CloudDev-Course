package demo;

import java.time.Duration;
//import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveDemoController {
//	private DummyDao dao;
	
//	@Autowired
//	public void setDao(DummyDao dao) {
//		this.dao = dao;
//	}
	
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
		List<Demo> list = IntStream.range(1, 10+1)
			.mapToObj(i->new Demo("message #" + i))
			.collect(Collectors.toList());
		
//		return Flux.just(
//				list.get(0),
//				list.get(1),
//				list.get(2));
		
		return Flux.fromIterable(list)
//				.delayElements(Duration.ofSeconds(10));
				.delayElements(Duration.ofMillis(300));
		
//		return Flux.error(new RuntimeException("error right on the beginning of service handling"));

		// infinite flux
//		return Flux.interval(Duration.ofMillis(300))
//			.map(val->new Demo("another message (" + new Date() + ")"));
	}
	
	@RequestMapping(path="/demoes",
			method = RequestMethod.DELETE)
	public Mono<Void> deleteAll() {
		return Mono.empty();
	}
}

