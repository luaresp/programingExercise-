package cl.luaresp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.luaresp.model.Course;

/**
 * JPA Repository for table Course
 * 
 * @author luaresp
 *
 */
public interface CourseRepository extends JpaRepository<Course, String> {

}
