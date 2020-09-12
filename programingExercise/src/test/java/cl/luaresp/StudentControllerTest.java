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

import cl.luaresp.model.JwtResponse;

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

}
