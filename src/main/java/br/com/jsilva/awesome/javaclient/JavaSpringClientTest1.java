package br.com.jsilva.awesome.javaclient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.jsilva.awesome.model.PageableResponse;
import br.com.jsilva.awesome.model.Student;

public class JavaSpringClientTest1 {
	public static void main(String[] args) {// OBS: Tem que tirar do metodo o pageable para funcionar+
		RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/api/v1/students")
				.basicAuthorization("joao_silva", "jpp123456").build();
		Student student = restTemplate.getForObject("/{id}", Student.class, 1);
		ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 1);
		System.out.println(student);
		System.out.println(forEntity.getBody());

		ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc", HttpMethod.GET,
				null, new ParameterizedTypeReference<PageableResponse<Student>>() {
				});
		System.out.println(exchange);

	}

}
