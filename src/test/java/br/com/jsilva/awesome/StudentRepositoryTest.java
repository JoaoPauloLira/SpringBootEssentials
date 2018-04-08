package br.com.jsilva.awesome;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jsilva.awesome.model.Student;
import br.com.jsilva.awesome.repository.StudentRepository;
import lombok.val;

@RunWith(SpringRunner.class)
@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private Student student;

	public StudentRepositoryTest() {
		this.student = new Student("joaopaulo", "joao.p.lira@gmail.com");
	}

	@Test
	public void createShouldPersisData() {
		this.studentRepository.save(student);
		assertThat(student.getId()).isNotNull();
		assertThat(student.getName()).isEqualTo("joaopaulo");
		assertThat(student.getEmail()).isEqualTo("joao.p.lira@gmail.com");
	}

	@Test
	public void deleteShouldRemoveData() {
		this.studentRepository.save(student);
		studentRepository.delete(student);
		assertThat(studentRepository.findOne(student.getId())).isNull();
	}

	@Test
	public void updateShouldChangeAndPersistData() {
		this.studentRepository.save(student);
		student.setName("joaopaulolira");
		student.setEmail("joaopaulolira@gmail.com");
		this.studentRepository.save(student);
		student = this.studentRepository.findOne(student.getId());
		assertThat(student.getName()).isEqualTo("joaopaulolira");
		assertThat(student.getEmail()).isEqualTo("joaopaulolira@gmail.com");
	}

	@Test
	public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
		val student2 = new Student("JoaoPaulo", "joao.p.lira222@gmail.com");
		this.studentRepository.save(student);
		this.studentRepository.save(student2);
		val studentList = studentRepository.findByNameIgnoreCaseContaining("joaopaulo");
		assertThat(studentList.size()).isEqualTo(2);
	}

	@Test
	public void createWhenNameIsNullShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo nome do estudante é obrigatório");
		this.studentRepository.save(new Student());
	}

	@Test
	public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		Student student = new Student();
		student.setName("JoaoPaulo");
		this.studentRepository.save(student);
	}

	@Test
	public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Não é um endereço de e-mail");
		Student student = new Student();
		student.setName("JoaoPaulo");
		student.setEmail("JoaoPaulo");
		this.studentRepository.save(student);
	}

}
