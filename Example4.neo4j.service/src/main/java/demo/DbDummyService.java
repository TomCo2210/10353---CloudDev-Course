package demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DbDummyService implements DummyService{
	private DummyCrud dummies;
	private SubsCrud subDummies;
	
	@Autowired
	public DbDummyService(DummyCrud dummies, SubsCrud subDummies) {
		super();
		this.dummies = dummies;
		this.subDummies = subDummies;
	}

	@Override
	@Transactional
	public Dummy create(Dummy dummy) {
		dummy.setId(null);
		return this.dummies
			.save(dummy); // UPSERT
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dummy> getAll(int size, int page) {
		return this.dummies
				.findAll(PageRequest.of(page, size, Direction.ASC, "id"))
				.getContent();
	}

	@Override
	@Transactional(readOnly = true)
	public Dummy getDummyById(Long id) {
		 
		Optional<Dummy> optional = this.dummies
				.findById(id); 
		
		if (optional.isPresent()) {
			return optional.get();
		}else {
			throw new DummyNotFoundExcetion("could not find object by id: " + id);
		}
	}

	@Override
	@Transactional
	public Dummy update(Long idToUpdate, Dummy update) {
		Dummy rv = this.getDummyById(idToUpdate);
		if (update.getDummyDate() != null) {
			rv.setDummyDate(update.getDummyDate());
		}
		
		if (update.getFirstName() != null) {
			rv.setFirstName(update.getFirstName());
		}

		if (update.getLastName() != null) {
			rv.setLastName(update.getLastName());
		} 

		return this.dummies
			.save(rv); // UPSERT
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.subDummies
			.deleteAll();
		
		this.dummies
			.deleteAll(); 
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dummy> getDummiesByIdRange(long min, long max, int size, int page) {
		return this.dummies
				.findAllByInnerValueBetween(min, max,
						PageRequest.of(page, size, Direction.ASC, "dummyDate", "id"));
	}
}
