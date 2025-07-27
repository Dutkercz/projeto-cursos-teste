package com.github.Dutkercz.demoPlataformaDeCursos.dtos.course;

public record RequestUpdateCourseDTO(
        String title,
        String description,
        String category
) {
}
