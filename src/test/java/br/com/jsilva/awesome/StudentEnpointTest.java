package br.com.jsilva.awesome;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.jsilva.awesome.model.Student;
import br.com.jsilva.awesome.repository.StudentRepository;
import lombok.val;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // -> por causa do (private MockMvc mockMvc;)
public class StudentEnpointTest {

	// Maneiras de fazer os testes
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private MockMvc mockMvc;

	@LocalServerPort
	private int port;

	@MockBean
	private StudentRepository studentRepository;

	@Before
	public void setup() {
		System.err.println(">>>>>>>>SETUP<<<<<<<<<");
		Student student = new Student(1L, "Teste", "teste@teste.com,br");
		BDDMockito.when(studentRepository.findOne(student.getId())).thenReturn(student);
	}

	@TestConfiguration
	static class Config {
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			System.err.println(">>>>>>>>CONFIG<<<<<<<<<");
			return new RestTemplateBuilder().basicAuthorization("joao_silva", "jpp123456");
		}
	}

	@Test
	public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
		System.out.println(port);
		restTemplate = restTemplate.withBasicAuth("1", "1");
		val response = restTemplate.getForEntity("/api/v1/students", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}

	@Test
	public void getStudentsByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
		System.out.println(port);
		restTemplate = restTemplate.withBasicAuth("1", "1");
		val response = restTemplate.getForEntity("/api/v1/students", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}

	@Test
	public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
		val students = asList(new Student(1L, "teste", "teste@teste.com"),
				new Student(2L, "teste2n", "teste2@teste.com"));
		BDDMockito.when(studentRepository.findAll()).thenReturn(students);
		val response = restTemplate.getForEntity("/api/v1/students", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getStudentsByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
		ResponseEntity<Student> response = restTemplate.getForEntity("/api/v1/students/{id}", Student.class, 1L);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getStudentsByIdWhenUsernameAndPasswordAreCorrectAndStudentDoesNotExistShouldReturnStatusCode404() {
		val response = restTemplate.getForEntity("/api/v1/students/{id}", Student.class, -1);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	@WithMockUser(username = "joao_silva", password = "jpp123456", roles = { "ADMIN" })
	public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200() throws Exception {
		BDDMockito.doNothing().when(studentRepository).delete(1L);
		// val exchange = restTemplate.exchange("/api/v1/students/{id}", DELETE, null,
		// String.class, 1L);
		// assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "joao_silva", password = "jpp123456", roles = { "ADMIN" })
	public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistShouldReturnStatusCode404() throws Exception {
		BDDMockito.doNothing().when(studentRepository).delete(1L);
		// ResponseEntity<String> exchange =
		// restTemplate.exchange("/api/v1/students/{id}", DELETE, null, String.class,
		// -1L);
		// Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/{id}", -1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void createWhenNameIsNullShouldReturnStatusCode400BadRequest() throws Exception {
		val student = new Student(3L, null, "teste3@teste.com");
		BDDMockito.when(studentRepository.save(student)).thenReturn(student);
		val response = restTemplate.postForEntity("/api/v1/students/", student, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
		assertThat(response.getBody()).contains("fieldMessage", "O campo nome do estudante é obrigatório");
	}

	@Test
	public void createShouldPersistDataAndReturnStatusCode201() throws Exception {
		val student = new Student(3L, "Teste3", "teste3@teste.com");
		BDDMockito.when(studentRepository.save(student)).thenReturn(student);
		val response = restTemplate.postForEntity("/api/v1/students/", student, Student.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getBody().getId()).isNotNull();
	}

}
