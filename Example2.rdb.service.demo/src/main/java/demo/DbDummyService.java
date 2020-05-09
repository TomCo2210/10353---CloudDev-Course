package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DbDummyService implements DummyService{
	private DummyCrud dummies;
	
	@Autowired
	public DbDummyService(DummyCrud dummies) {
		super();
		this.dummies = dummies;
	}

	@Override
	@Transactional
	public Dummy create(Dummy dummy) {
		dummy.setId(null);
		return this.dummies
			.save(dummy); // INSERT / SELECT + UPDATE = UPSERT
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dummy> getAll() {
		List<Dummy> rv = new ArrayList<>();
		for (Dummy d :this.dummies.findAll()) { // SELECT 
			rv.add(d);
		}
		return rv;
	}

	@Override
	@Transactional(readOnly = true)
	public Dummy getDummyById(Long id) {
		 
		Optional<Dummy> optional = this.dummies
				.findById(id); // SELECT
		
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
			.save(rv); // SELECT INSERT / **UPDATE**
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.dummies
			.deleteAll(); // DELETE
		
	}

}
