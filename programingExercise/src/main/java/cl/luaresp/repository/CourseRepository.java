package cl.luaresp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.luaresp.model.Course;

/**
 * JPA Repository for table Course
 * 
 * @author luaresp
 *
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

	@Query(value = "select new cl.luaresp.model.Course(c.id, c.code, c.name) from Course c")
	Page<Course> findAllPage(Pageable pageable);
	
	@Query("select new cl.luaresp.model.Course(c.id, c.code, c.name) from Course c where c.code = :code")
	Slice<Course> findByCode(@Param("code") String code);

}
