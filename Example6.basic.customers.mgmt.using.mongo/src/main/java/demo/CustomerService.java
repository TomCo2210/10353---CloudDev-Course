package demo;

import java.util.List;

public interface CustomerService {
	public CustomerBoundary create (CustomerBoundary newCustomer);
	public CustomerBoundary getByEmail (String email);
	public List<CustomerBoundary> getAll (int size, int page, boolean asc, String sortBy);
	public List<CustomerBoundary> getByLastName (String lastName, int size, int page, boolean asc, String sortBy);
	
	public void cleanup();
}
