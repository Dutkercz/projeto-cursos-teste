package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;

public record EntityResponseDTO(
        Long id,
        String name,
        String email,
        String role
) {

    public EntityResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user instanceof Student ? "Aluno" : "Instrutor");
    }
}
