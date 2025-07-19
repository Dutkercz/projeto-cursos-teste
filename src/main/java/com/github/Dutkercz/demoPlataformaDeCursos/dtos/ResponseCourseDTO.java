package com.github.Dutkercz.demoPlataformaDeCursos.dtos;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;

public record ResponseCourseDTO(
        String title,
        String description,
        String category,
        String instructor
) {
    public ResponseCourseDTO(Course course) {
        this(course.getTitle(),
            course.getDescription(),
            course.getCategory(),
            course.getInstructor().getName());
    }
}
