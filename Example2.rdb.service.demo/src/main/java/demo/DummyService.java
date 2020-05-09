package demo;

import java.util.List;

// implement a higher level REST API layer
// implement this interface using DAL - Data Access Layer
public interface DummyService {
	// Create - INSERT
	public Dummy create (Dummy dummy);
	
	// Read All - SELECT 
	public List<Dummy> getAll();
	
	// Read One Item - SELECT
	public Dummy getDummyById (Long id);
	
	// Update - UPDATE
	public Dummy update (Long idToUpdate, Dummy update);
	
	// Delete - DELETE
	public void deleteAll ();
	
}
