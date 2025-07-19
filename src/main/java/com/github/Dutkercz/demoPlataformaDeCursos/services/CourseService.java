package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.CourseRepository;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseCourseDTO createNewCourse(RequestCourseDTO requestDTO, String userEmail){

        Course course = courseRepository.save(new Course(null, requestDTO.title(), requestDTO.description(),
                requestDTO.category(), null));

        return new ResponseCourseDTO(course);
    }
}
