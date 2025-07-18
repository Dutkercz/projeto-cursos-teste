package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User{
}
