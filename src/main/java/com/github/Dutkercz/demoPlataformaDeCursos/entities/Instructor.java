package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User{

    public Instructor() {
    }

    public Instructor(Long id, String name, String password, String email, String cpf) {
        super(id, name, password, email, cpf);
    }
}
