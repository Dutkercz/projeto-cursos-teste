package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{

    public Student() {
    }

    public Student(Long id, String name, String password, String email, String cpf) {
        super(id, name, password, email, cpf);
    }
}
