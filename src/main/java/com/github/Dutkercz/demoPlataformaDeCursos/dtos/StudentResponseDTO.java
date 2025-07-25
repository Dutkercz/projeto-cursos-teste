package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;

public record StudentResponseDTO(
        String studentName
) {
    public StudentResponseDTO(Student student){
        this(student.getName());
    }
}
