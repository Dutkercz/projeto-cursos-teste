package com.github.Dutkercz.demoPlataformaDeCursos.dtos.user;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String role
) {

    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(),
               user instanceof Student ? "Aluno" : user instanceof Instructor ? "Instrutor" : "Erro no role do usuário");
    }
}
