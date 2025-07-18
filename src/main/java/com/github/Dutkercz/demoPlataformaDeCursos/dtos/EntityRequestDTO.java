package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EntityRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String password,

        @Pattern(regexp = "^\\d{11}$")
        String cpf,

        @NotBlank @Email
        String email,

        @NotBlank
        String role

) {
}
