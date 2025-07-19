package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;

public record ResponseCourseDTO(
        Long id,
        String title,
        String description,
        String category,
        String instructor
) {
    public ResponseCourseDTO(Course course) {
        this(course.getId(),
            course.getTitle(),
            course.getDescription(),
            course.getCategory(),
            course.getInstructor().getName());
    }
}
