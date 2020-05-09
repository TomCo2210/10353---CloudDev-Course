package demo;

import java.util.List;

// implement a higher level REST API layer
// implement this interface using DAL - Data Access Layer
public interface DummyService {
	// Create - 
	public Dummy create (Dummy dummy);
	
	// Read All - 
	public List<Dummy> getAll(int size, int page);
	
	// Read One Item - 
	public Dummy getDummyById (Long id);
	
	// Update - 
	public Dummy update (Long idToUpdate, Dummy update);
	
	// Delete - 
	public void deleteAll ();
	
	public List<Dummy> getDummiesByIdRange(long min, long max, int size, int page);
	
}
