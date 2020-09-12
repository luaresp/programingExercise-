package cl.luaresp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.luaresp.model.Student;

/**
 * JPA Repository for table Student
 * 
 * @author luaresp
 *
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
