package cl.luaresp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cl.luaresp.model.Course;
import cl.luaresp.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CourseController {

	@Autowired
	private CourseRepository courseRepo;

	@GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Course>> getAllCourses(@PageableDefault(page = 0, size = 10) @SortDefault.SortDefaults({
			@SortDefault(sort = "code", direction = Sort.Direction.ASC, caseSensitive = false) }) Pageable pageable) {

		Page<Course> result = courseRepo.findAllPage(pageable);

		if (result.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	


	@GetMapping(value = "/courses/{id}")
	public ResponseEntity<Slice<Course>> getCoursesById(@PathVariable("id") String code) {

		Slice<Course> result = courseRepo.findByCode(code);
		
		if (result.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}
