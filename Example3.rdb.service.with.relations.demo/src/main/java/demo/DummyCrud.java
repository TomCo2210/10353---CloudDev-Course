package demo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface DummyCrud extends PagingAndSortingRepository<Dummy, Long>{ 
	//extends CrudRepository<Dummy, Long>{
	
	public List<Dummy> findAllByIdBetween(
			@Param("minValue") long minValue, 
			@Param("maxValue") long maxValue,
			Pageable pageable);
	
}
