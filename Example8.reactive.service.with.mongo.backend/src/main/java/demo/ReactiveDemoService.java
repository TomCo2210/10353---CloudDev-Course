package demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveDemoService {

	public Flux<Dummy> getReactiveData();

	public Mono<Void> deleteAllData();

	public Mono<Dummy> storeNewDummy(Dummy dummy);
	
	public Mono<Void> updateData(String dummyId, String newData); 

}
