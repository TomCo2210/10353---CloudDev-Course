package demo;

import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DummyInit implements CommandLineRunner{
	private DummyService dummies;
	
	@Autowired	
	public DummyInit(DummyService dummies) {
		super();
		this.dummies = dummies;
	}



	@Override
	public void run(String... args) throws Exception {
		this.dummies.deleteAll();
		
		Random random = new Random(System.currentTimeMillis());
		IntStream.range(0, 100)
			.mapToObj(i->{
				Dummy dummy = new Dummy("d" + i, "demo", new Date());
				dummy.setInnerValue((long)i);
				return dummy;
			})
//			.map(this.dummies::create)
//			.peek(d->d.setInnerValue(d.getId()))
			.map(dummy->{
				IntStream.range(0, random.nextInt(4))
				.mapToObj(i->new SubDummy("I am subdummy #" + i))
				.forEach(dummy::addSubDummy);
				return dummy;
			})
			.forEach(this.dummies::create);		
	}

}
