package cl.luaresp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.luaresp.exception.BadRequestException;
import cl.luaresp.model.Course;
import cl.luaresp.repository.CourseRepository;


@RestController
@CrossOrigin
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

		Page<Course> result = courseRepo.findAllPage(pageable);

		if (result.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	/**
	 * allows get a specific course
	 * @param code
	 * @return
	 * @throws BadRequestException 
	 */
	@GetMapping(value = "/courses/{code}")
	public ResponseEntity<Slice<Course>> getCoursesById(@PathVariable("code") String code) throws BadRequestException {
		
		if(code.length() > 4)
			throw new BadRequestException("id: length >4");

		Slice<Course> result = courseRepo.findByCode(code);

		if (result.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@PostMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> setcourse(@RequestBody @Validated Course course) {
		
	
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
