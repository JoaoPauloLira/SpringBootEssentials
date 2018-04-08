package br.com.jsilva.awesome.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends AbstractEntity {

	public Student() {
	}

	public Student(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Student(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	@NotEmpty(message = "O campo nome do estudante é obrigatório")
	private String name;
	@Email
	@NotEmpty(message = "o campo email é obrigatório")
	private String email;

	@Override
	public String toString() {
		return "Student{" + "name='" + name + '\'' + ", email='" + email + '\'' + '}';
	}

}
