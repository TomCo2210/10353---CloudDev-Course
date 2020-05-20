package demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerDao extends PagingAndSortingRepository<CustomerEntity, String>{
	public Optional<CustomerEntity> findOneByEmail(
			@Param("email") String email);
	
//	public List<CustomerEntity> findAllByName_Last(
//			@Param("lastName") String lastName,
//			Pageable pageable);

	public List<CustomerEntity> findAllByLast(
			@Param("lastName") String lastName,
			Pageable pageable);

}
