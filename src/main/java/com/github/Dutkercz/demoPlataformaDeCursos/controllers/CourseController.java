package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public ResponseEntity<ResponseCourseDTO> createNewCourse(@Valid @RequestBody RequestCourseDTO requestDTO,
                                                             UriComponentsBuilder builder){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseCourseDTO courseDTO = courseService.createNewCourse(requestDTO, userEmail);
    }
}
