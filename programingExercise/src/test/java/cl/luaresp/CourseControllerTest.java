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
import cl.luaresp.model.JwtRequest;
import cl.luaresp.model.JwtResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void getValidateAuthenticateMethod() throws Exception {

		JwtRequest request = new JwtRequest();
		request.setUsername("prueba");
		request.setPassword("prueba123");

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk());

		request.setPassword("prueba124");
		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(request)))
				.andExpect(status().is4xxClientError());

		mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}

	@Test
	public void getCourses() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();

		mockMvc.perform(MockMvcRequestBuilders.get("/courses").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/courses?page=1&size=10")
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/courses?page=1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/courses?size=10").contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}

	@Test
	public void getCoursesByCode() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();

		mockMvc.perform(MockMvcRequestBuilders.get("/courses/{code}", "44444444").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isBadRequest());

		mockMvc.perform(MockMvcRequestBuilders.get("/courses/{code}", "4444").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isNotFound());

		mockMvc.perform(MockMvcRequestBuilders.get("/courses/{code}", "MA10").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
	}

	@Test
	public void postCourses() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();

		Course course = new Course();
		course.setCode("LL10");
		course.setName("English 10");

		mockMvc.perform(MockMvcRequestBuilders.post("/courses").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(course))).andExpect(status().isCreated());

		course = new Course();
		course.setCode("LL100");
		course.setName("English 10");
		mockMvc.perform(MockMvcRequestBuilders.post("/courses").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(course))).andExpect(status().isBadRequest());

		course = new Course();
		course.setCode("L.00");
		course.setName("English 10");
		mockMvc.perform(MockMvcRequestBuilders.post("/courses").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(course))).andExpect(status().isBadRequest());

	}

	@Test
	public void updateCourses() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();

		Course course = new Course();
		course.setCode("QU00");
		course.setName("Quimica 10");

		mockMvc.perform(MockMvcRequestBuilders.put("/courses/{code}", course.getCode()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(course))).andExpect(status().isNotFound());

		course = new Course();
		course.setCode("QU10");
		course.setName("Quimica 10");
		mockMvc.perform(MockMvcRequestBuilders.put("/courses/{code}", course.getCode()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(course))).andExpect(status().isOk());

		course = new Course();
		course.setCode("Q.10");
		course.setName("Quimica 10");
		mockMvc.perform(MockMvcRequestBuilders.put("/courses/{code}", course.getCode()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(course))).andExpect(status().isBadRequest());

		course = new Course();
		course.setCode("NA100");
		course.setName("Quimica 10");
		mockMvc.perform(MockMvcRequestBuilders.put("/courses/{code}", course.getCode()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
				.content(mapper.writeValueAsString(course))).andExpect(status().isBadRequest());

	}

	@Test
	public void deleteCourse() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/token").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JwtResponse response = mapper.readValue(actualResponseBody, JwtResponse.class);
		String token = response.getJwttoken();

		mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{code}", "LI20").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{code}", "AA20").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(status().isNotFound());

		mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{code}", "AA020").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(status().isBadRequest());

		mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{code}", "A.10").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(status().isBadRequest());
	}

}
