package cl.luaresp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cl.luaresp.model.Student;

public interface StudentRepository {

	@Query(value  = "select rut, name, lastname, age, courseId from Student s ")
	List<Student> findAllPage();
	
}
