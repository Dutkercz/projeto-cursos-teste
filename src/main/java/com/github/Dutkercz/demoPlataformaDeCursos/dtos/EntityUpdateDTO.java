package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

import jakarta.validation.constraints.Email;

public record EntityUpdateDTO(
        String name,
        String password,

        @Email(message = "Digite um email válido")
        String email
) {
}
