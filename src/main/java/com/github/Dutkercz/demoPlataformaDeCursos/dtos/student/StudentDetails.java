package com.github.Dutkercz.demoPlataformaDeCursos.dtos.student;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;

import static java.time.format.DateTimeFormatter.ofPattern;

public record StudentDetails(
        Long id,
        String name,
        String email,
        String registerDate,
        Boolean isActive
) {
    public StudentDetails(Student student) {
        this(student.getId(),
                student.getName(),
                student.getEmail(),
                ofPattern("dd/MM/yyyy HH:mm").format(student.getRegisterDate()),
                student.getIsActive()
        );
    }
}
