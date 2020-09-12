package cl.luaresp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.luaresp.model.Course;

/**
 * JPA Repository for table Course
 * 
 * @author luaresp
 *
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

}
