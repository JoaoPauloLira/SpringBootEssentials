package br.com.jsilva.awesome.javaclient;

import br.com.jsilva.awesome.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class JavaSpringClientTest {
    public static void main(String[] args) {//OBS: Tem que tirar do metodo o pageable para funcionar+
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/api/v1/students")
                .basicAuthorization("joao_silva","jpp123456")
                .build();
        Student student = restTemplate.getForObject("/{id}", Student.class, 1);
        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}",Student.class,1);
        System.out.println(student);
        System.out.println(forEntity.getBody());
        Student[] students = restTemplate.getForObject("/", Student[].class);
        System.out.println(Arrays.toString(students));
        ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });
        System.out.println(exchange.getBody());



    }

}
