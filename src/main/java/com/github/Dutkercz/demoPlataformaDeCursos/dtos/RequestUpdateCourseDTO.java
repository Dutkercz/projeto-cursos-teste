package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

public record RequestUpdateCourseDTO(
        String title,
        String description,
        String category
) {
}
