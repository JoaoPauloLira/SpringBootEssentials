package br.com.jsilva.awesome.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Student extends AbstractEntity{

    @NotEmpty(message = "O campo nome do estudante é obrigatório")
    private String name;
    @Email @NotEmpty(message = "o campo email é obrigatório")
    private String email;
}
