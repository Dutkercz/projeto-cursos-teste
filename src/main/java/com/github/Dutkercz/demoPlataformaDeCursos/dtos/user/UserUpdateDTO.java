package com.github.Dutkercz.demoPlataformaDeCursos.dtos.user;

import jakarta.validation.constraints.Email;

public record UserUpdateDTO(
        String name,
        String password,

        @Email(message = "Digite um email válido")
        String email
) {
}
