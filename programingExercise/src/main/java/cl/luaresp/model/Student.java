package cl.luaresp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "Student")
public class Student implements Serializable
{

	private static final long serialVersionUID = -3123193401811639013L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonFormat(pattern = "^([0-9]{1,3}((\\.[0-9]{1,3}){2}|([0-9]{1,3}){2})-[0-9kK])$")
	private String rut;
	private String name;
	private String lastName;
	@JsonFormat(pattern = "^([0-9{1,3}])$")
	private int age;
	private int courseId;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Studentimplements [id=");
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
		builder.append(courseId);
		builder.append("]");
		return builder.toString();
	}

}
