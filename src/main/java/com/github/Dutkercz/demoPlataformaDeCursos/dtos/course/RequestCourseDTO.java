package com.github.Dutkercz.demoPlataformaDeCursos.dtos.course;

import jakarta.validation.constraints.NotBlank;

public record RequestCourseDTO(
        @NotBlank(message = "O campo titulo não pode estar em branco.")
        String title,

        @NotBlank(message = "O campo descrição não pode estar em branco.")
        String description,

        @NotBlank(message = "O campo categoria não pode estar em branco.")
        String category
) {
}
