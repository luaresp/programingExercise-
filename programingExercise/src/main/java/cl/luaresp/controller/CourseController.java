package cl.luaresp.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.luaresp.exception.BadRequestException;
import cl.luaresp.exception.NotFoundException;
import cl.luaresp.model.Course;
import cl.luaresp.repository.CourseRepository;

@RestController
@CrossOrigin
@Validated
/**
 * this controller implements the necessary logic to manipulate a course
 * 
 * @author luaresp
 *
 */
public class CourseController {

	@Autowired
	private CourseRepository courseRepo;

	/**
	 * allows get all courses
	 * 
	 * @param pageable
	 * @return
	 */
	@GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Page<Course>> getAllCourses(@PageableDefault(page = 0, size = 10) @SortDefault.SortDefaults({
			@SortDefault(sort = "code", direction = Sort.Direction.ASC, caseSensitive = false) }) Pageable pageable) {

		Page<Course> result = courseRepo.findAll(pageable);

		if (result.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	/**
	 * allows get a specific course
	 * 
	 * @param code
	 * @return
	 * @throws BadRequestException
	 */
	@GetMapping(value = "/courses/{code}")
	public ResponseEntity<Optional<Course>> getCoursesByCode(
			@Valid @PathVariable("code") @Pattern(regexp = "^[A-Za-z0-9\\\\-_]{1,4}$") String code) {

		Optional<Course> result = courseRepo.findById(code);

		if (result.isEmpty())
			throw new NotFoundException("code: " + code + " not found ");

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> setcourse(@Valid @RequestBody Course course) {

		Optional<Course> result = courseRepo.findById(course.getCode());

		if (!result.isEmpty())
			throw new BadRequestException("code: " + course.getCode() + " existe previamente");

		courseRepo.save(course);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/courses/{code}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> updateCourse(
			@Valid @PathVariable("code") @Pattern(regexp = "^[A-Za-z0-9\\\\-_]{1,4}$") String code,
			@Valid @RequestBody Course course) {

		Optional<Course> result = courseRepo.findById(code);

		if (result.isEmpty())
			throw new NotFoundException("code: " + code + " no encontrado ");

		Course cour = result.get();
		cour.setName(course.getName());

		courseRepo.save(cour);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/courses/{code}", headers = "Accept=application/json")
	public ResponseEntity<String> deleteCourse(
			@Valid @PathVariable("code") @Pattern(regexp = "^[A-Za-z0-9\\\\-_]{1,4}$") String code) {

		Optional<Course> result = courseRepo.findById(code);

		if (result.isEmpty())
			throw new NotFoundException("code: " + code + " no encontrado ");

		courseRepo.deleteById(code);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
