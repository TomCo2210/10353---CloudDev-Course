package demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	private CustomerService customers;
	
	@Autowired
	public void setCustomers(CustomerService customers) {
		this.customers = customers;
	}
	
	@RequestMapping(path = "/customers",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary create (@RequestBody CustomerBoundary newCustomer) {
		return this.customers.create(newCustomer);
	}
	
	@RequestMapping(path = "/customers/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary getByEmail (@PathVariable("email") String email) {
		return this.customers.getByEmail(email);
	}
	
	@RequestMapping(path = "/customers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerBoundary[] getAll (
			@RequestParam(name = "size", required = false, defaultValue = "10") int size, 
			@RequestParam(name = "page", required = false, defaultValue = "0") int page, 
			@RequestParam(name = "order", required = false, defaultValue = "ASC") OrderEnum asc, 
			@RequestParam(name = "sortBy", required = false, defaultValue = "email") CustomerProperty sortBy,
			@RequestParam(name = "byLastName", required = false, defaultValue = "") String byLastName){
		
		List<CustomerBoundary> rv;
		if (byLastName.isEmpty()) {
			rv = this.customers.getAll(size, page, asc.equals(OrderEnum.ASC), sortBy.name());
		}else {
			rv = this.customers.getByLastName(byLastName, size, page, asc.equals(OrderEnum.ASC), sortBy.name());
		}
		
		return rv
				.toArray(new CustomerBoundary[0]);
	}
	
	
	@RequestMapping(path = "/customers",
			method = RequestMethod.DELETE)
	public void cleanup() {
		this.customers.cleanup();
	}
}
