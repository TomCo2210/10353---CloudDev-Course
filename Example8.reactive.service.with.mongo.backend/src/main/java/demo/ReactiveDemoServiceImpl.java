package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service // BL Layer
public class ReactiveDemoServiceImpl implements ReactiveDemoService{
	private DummyDao dummies;
	
	@Autowired
	public ReactiveDemoServiceImpl(DummyDao dummies) {
		super();
		this.dummies = dummies;
	}

	@Override
	public Flux<Dummy> getReactiveData() {
		return this.dummies
				.findAll(Sort.by(Direction.ASC, "id"));
	}

	@Override
	public Mono<Void> deleteAllData() {
		return this.dummies
			.deleteAll();
	}

	@Override
	public Mono<Dummy> storeNewDummy(Dummy dummy) {
		return this.dummies
				.save(dummy);
	}
	
	@Override
	public Mono<Void> updateData(String dummyId, String newData) {
		return this.dummies
			.findById(dummyId) // Mono<Dummy>
			.flatMap(oldDummy->{
				oldDummy.setData(newData);
				return this.dummies.save(oldDummy);
			})// Mono<Dummy>
			.flatMap(d->Mono.empty());// Mono<Void>
	}
}
