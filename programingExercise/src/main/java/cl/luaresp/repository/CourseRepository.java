package cl.luaresp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cl.luaresp.model.Course;

public interface CourseRepository {

	@Query(value = "select c.name, c.code from Course c ")
	List<Course> findAllPage();

}
