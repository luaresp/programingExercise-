package cl.luaresp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Entity
@Data
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "rut", "name", "lastName", "age", "course" })
public class Student implements Serializable {

	private static final long serialVersionUID = -3123193401811639013L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = true, updatable = false, unique = true)
	@JsonIgnore
	private Long id;

	@Id
	@NotNull
	@Pattern(regexp = "^([0-9]{1,3}((\\.[0-9]{1,3}){2}|([0-9]{1,3}){2})-[0-9kK])$")
	@Column(name = "rut", length = 11, nullable = false, unique = true)
	@JsonProperty(value = "rut", required = true, defaultValue = "")
	private String rut;

	@NotNull
	@Size(message = "name: size not permit", min = 1, max = 20)
	@Column(name = "name", length = 20, nullable = false, unique = false)
	@JsonProperty(value = "name", required = true, defaultValue = "")
	private String name;

	@NotNull
	@Size(message = "lastName: size not permit", min = 1, max = 20)
	@Column(name = "lastname", length = 20, nullable = false, unique = false)
	@JsonProperty(value = "lastName", required = true, defaultValue = "")
	private String lastName;

	@NotNull
	@Min(18)
	@Column(name = "age", nullable = false, unique = false)
	@JsonProperty(value = "age", required = true, defaultValue = "0")
	private int age;

	@NotNull
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Course_code", referencedColumnName = "code")
	@JsonProperty(value = "course", required = true, defaultValue = "")
	private Course course;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Student [id=");
		builder.append(id);
		builder.append(", rut=");
		builder.append(rut);
		builder.append(", name=");
		builder.append(name);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", age=");
		builder.append(age);
		builder.append(", course=");
		builder.append(course);
		builder.append("]");
		return builder.toString();
	}

}
