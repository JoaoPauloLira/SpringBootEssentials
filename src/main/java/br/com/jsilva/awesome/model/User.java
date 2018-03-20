package br.com.jsilva.awesome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter @Setter
@Entity
public class User extends AbstractEntity {

    @NotEmpty @Column(unique = true)
    private String userName;
    @NotEmpty @JsonIgnore
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private boolean admin;
}
