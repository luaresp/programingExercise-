package cl.luaresp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.luaresp.model.Course;
import cl.luaresp.model.JwtResponse;
import cl.luaresp.model.Student;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void getStudent() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();

		mockMvc.perform(MockMvcRequestBuilders.get("/students").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/students?page=0&size=10")
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/students?page=0").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/students?size=10").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());

	}

	@Test
	public void getStudentByRut() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();

		mockMvc.perform(MockMvcRequestBuilders.get("/students/{code}", "4444").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isBadRequest());

		mockMvc.perform(MockMvcRequestBuilders.get("/students/{code}", "88888888-8").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isNotFound());

		mockMvc.perform(MockMvcRequestBuilders.get("/students/{code}", "44444444-4").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

	}

	@Test
	public void postStudents() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();
		
		Course course = new Course();
		course.setCode("MA10");
		
		Student stud = new Student();
		stud.setRut("99999999-9");
		stud.setName("Alejandro");
		stud.setLastName("Esparza");
		stud.setAge(18);
		stud.setCourse(course);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/students").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(stud))).andExpect(status().isCreated());
		
		stud = new Student();
		stud.setRut("77777777-7");
		stud.setName("Alejandro");
		stud.setLastName("Esparza");
		stud.setAge(13);
		stud.setCourse(course);
		mockMvc.perform(MockMvcRequestBuilders.post("/students").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(stud))).andExpect(status().isBadRequest());
		
		stud = new Student();
		stud.setRut("9999");
		stud.setName("Alejandro");
		stud.setLastName("Esparza");
		stud.setAge(18);
		stud.setCourse(course);
		mockMvc.perform(MockMvcRequestBuilders.post("/students").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(stud))).andExpect(status().isBadRequest());
		
		stud = new Student();
		stud.setRut("77777777-7");
		stud.setLastName("Esparza");
		stud.setAge(18);
		stud.setCourse(course);
		mockMvc.perform(MockMvcRequestBuilders.post("/students").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(stud))).andExpect(status().isBadRequest());
		
		course = new Course();
		course.setCode("PP01");
		
		stud = new Student();
		stud.setRut("77777777-7");
		stud.setName("Alejandro");
		stud.setLastName("Esparza");
		stud.setAge(18);
		stud.setCourse(course);
		mockMvc.perform(MockMvcRequestBuilders.post("/students").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(stud))).andExpect(status().isNotFound());
	}

}
