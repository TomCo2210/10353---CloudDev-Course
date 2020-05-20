package demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
	private CustomerDao customers;
	private SimpleDateFormat formatter;
	
	@Autowired
	public CustomerServiceImpl(CustomerDao customers) {
		this.customers = customers;
		this.formatter = new SimpleDateFormat("dd-MM-yyyy");
	}

	@Override
//	@Transactional
	public CustomerBoundary create(CustomerBoundary newCustomer) {
		if (newCustomer.getEmail() == null 
				|| newCustomer.getEmail().trim().isEmpty()
				|| !newCustomer.getEmail().contains("@")) {
			throw new RuntimeException("invalid email for new customer"); 
		}
		if (this.customers
				.findOneByEmail(newCustomer.getEmail())
				.isPresent()) {
			throw new RuntimeException("a user with this email already exists");
		}
		
		CustomerEntity entity = this.toEntity(newCustomer);
		entity.setId(null);
		return this.fromEntity(
				this.customers
				.save(entity));
	}

	private CustomerBoundary fromEntity(CustomerEntity entity) {
		CustomerBoundary rv = new CustomerBoundary();
		rv.setBirthdate(this.formatter.format(entity.getBirthdate()));
		rv.setCustomerDetails(entity.getCustomerDetails());
		rv.setEmail(entity.getEmail());
		
//		rv.setName(new NameBoundary(entity.getName()));
		rv.setName(new NameBoundary(entity.getFirst(), entity.getLast()));
		
		return rv;
	}

	private CustomerEntity toEntity(CustomerBoundary newCustomer) {
		CustomerEntity entity = new CustomerEntity();
		try {
			entity.setBirthdate(this.formatter.parse(newCustomer.getBirthdate()));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
		entity.setCustomerDetails(newCustomer.getCustomerDetails());
		
		entity.setEmail(newCustomer.getEmail());
		
//		entity.setName(newCustomer.getName().toEntity());
		if (newCustomer.getName() != null) {
			entity.setFirst(newCustomer.getName().getFirstName());
			entity.setLast(newCustomer.getName().getLastName());
		}
		
//		entity.setId(???);
		
		return entity;
		
	}

	@Override
//	@Transactional(readOnly = true)
	public CustomerBoundary getByEmail(String email) {
		return this.fromEntity(this.customers
				.findOneByEmail(email)
					.orElseThrow(()->new CustomerNotFoundException("could not find customer by email: " + email)));
	}

	@Override
//	@Transactional(readOnly = true)
	public List<CustomerBoundary> getAll(int size, int page, boolean asc, String sortBy) {
		return this.customers
				.findAll(
					PageRequest.of(
						page, size, 
						asc?Direction.ASC:Direction.DESC, 
						sortBy))
				.getContent()
				.stream()
				.map(this::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
//	@Transactional(readOnly = true)
	public List<CustomerBoundary> getByLastName(String lastName, int size, int page, boolean asc, String sortBy) {
		return this.customers
				.findAllByLast(
						lastName, 
					PageRequest.of(
						page, size, 
						asc?Direction.ASC:Direction.DESC, 
						sortBy))
				.stream()
				.map(this::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
//	@Transactional
	public void cleanup() {
		this.customers
			.deleteAll();

	}

}
