package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;

public record EntityResponseDTO(
        Long id,
        String name,
        String email,
        String role
) {

    public EntityResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(),
               user instanceof Student ? "Aluno" : user instanceof Instructor ? "Instrutor" : "Erro no role do usuário");
    }
}
