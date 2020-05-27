package demo;

import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Component
public class ReactiveDemoConsumer implements CommandLineRunner{
	@Override
	public void run(String... args) throws Exception {
		String url = "http://localhost:8092/demoes";
		
		WebClient webClient = WebClient.create(url);
		System.err.println("consumer initialized and requested GET /demoes");
		
		// operation #1 - create many items of data
		Flux.fromStream(
			IntStream.range(1, 100+1)
			.mapToObj(i->"message #" + i)
		)// Flux<String>
		.flatMap(messageString->
			webClient
				.post()
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new Demo(messageString))
				.retrieve()
				.bodyToMono(Demo.class)
			)// Flux<Demo>
			
		.subscribe(
				postOutput->System.err.println("successfully created new data"),
				e->e.printStackTrace(),
				()-> // on completion of the flux
			
		
		// operation #2 - get reactively
		//Flux<Map> results = 
		webClient
			.get()
			.accept(MediaType.TEXT_EVENT_STREAM)
			.retrieve()
			.bodyToFlux(Map.class)
		
//		results
//			.subscribe(); // do nothing consumer
		
//			.subscribe(demo->
//				System.err.println("retrieved new data: " + demo)); // handle successful events only
		
		// delete only after reading all available data from former Flux
		.doOnComplete(()->
		
			// operation #3 - delete reactively
			webClient
				.delete()
				.retrieve()
				.bodyToMono(Void.class)
				.subscribe(demo->
			System.err.println("*** delete operation retrieved new data: " + demo),
			
			error->System.err.println("*** something wrong happened while deleting"),// handle errors
			
			()->System.err.println("*** done consuming data from service using delete"),// complete runnable
			
			subscription->subscription.request(20) // subscription handling
			
			)
		)
		.subscribe(demo->
				System.err.println("retrieved new data: " + demo),
				
				error->
				//System.err.println("something wrong happened"),
				error.printStackTrace(), // handle errors
				
				()->System.err.println("done consuming data from service")//,// complete runnable
			
//				subscription->subscription.request(20) // subscription handling
				
				)
		);

	}

}
