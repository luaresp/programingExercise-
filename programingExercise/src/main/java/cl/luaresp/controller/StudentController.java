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
import cl.luaresp.model.Student;
import cl.luaresp.repository.CourseRepository;
import cl.luaresp.repository.StudentRepository;
import cl.luaresp.service.StudentService;

@RestController
@CrossOrigin
@Validated
public class StudentController {

	@Autowired(required = true)
	private StudentRepository studentRepo;

	@Autowired(required = true)
	private CourseRepository courseRepo;

	@Autowired(required = true)
	private StudentService studentServ;

	private final String expRexRUT = "^([0-9]{1,3}((\\\\\\\\.[0-9]{1,3}){2}|([0-9]{1,3}){2})-[0-9kK])$";

	@GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Page<Student>> getAllStudents(
			@PageableDefault(page = 0, size = 10) @SortDefault.SortDefaults({
					@SortDefault(sort = "rut", direction = Sort.Direction.ASC, caseSensitive = false) }) Pageable pageable) {

		Page<Student> result = studentRepo.findAll(pageable);

		if (result.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping(value = "/students/{rut}")
	public ResponseEntity<Optional<Student>> getStudentByRut(
			@Valid @PathVariable("rut") @Pattern(regexp = expRexRUT) String rut) {

		if (!studentServ.validaModulo11(rut))
			throw new BadRequestException("rut: " + rut + " fails module 11 ");

		Optional<Student> result = studentRepo.findById(rut);

		if (result.isEmpty())
			throw new NotFoundException("rut: " + rut + " not found ");

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> setStudent(@Valid @RequestBody Student student) {

		if (!studentServ.validaModulo11(student.getRut()))
			throw new BadRequestException("rut: " + student.getRut() + " fails module 11 ");

		Optional<Student> result = studentRepo.findById(student.getRut());

		if (!result.isEmpty())
			throw new BadRequestException("rut: " + student.getRut() + " existe previamente");

		Optional<Course> course = courseRepo.findById(student.getCourse().getCode());

		if (course.isEmpty())
			throw new NotFoundException("course.code: " + student.getCourse().getCode() + " not found ");

		student.setCourse(course.get());
		studentRepo.save(student);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping(value = "/students/{rut}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> updateStudent(@Valid @PathVariable("rut") @Pattern(regexp = expRexRUT) String rut,
			@Valid @RequestBody Student student) {

		if (!studentServ.validaModulo11(rut))
			throw new BadRequestException("rut: " + rut + " fails module 11 ");

		student.setRut(rut);

		Optional<Student> result = studentRepo.findById(rut);

		if (result.isEmpty())
			throw new NotFoundException("rut: " + rut + " not found");

		Optional<Course> course = courseRepo.findById(student.getCourse().getCode());

		if (course.isEmpty())
			throw new NotFoundException("course.code: " + student.getCourse().getCode() + " not found ");

		Student stud = result.get();
		stud.setName(student.getName());
		stud.setLastName(student.getLastName());
		stud.setAge(student.getAge());
		stud.setCourse(course.get());

		studentRepo.save(stud);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/students/{rut}", headers = "Accept=application/json")
	public ResponseEntity<String> deleteCourse(@Valid @PathVariable("rut") @Pattern(regexp = expRexRUT) String rut) {

		if (!studentServ.validaModulo11(rut))
			throw new BadRequestException("rut: " + rut + " fails module 11 ");

		Optional<Student> result = studentRepo.findById(rut);

		if (result.isEmpty())
			throw new NotFoundException("rut: " + rut + " not found");

		studentRepo.deleteById(rut);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
