package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<ResponseCourseDTO> createNewCourse(@Valid @RequestBody RequestCourseDTO requestDTO,
                                                             UriComponentsBuilder builder){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseCourseDTO courseDTO = courseService.createNewCourse(requestDTO, userEmail);
        URI uri = builder.path("/courses/{id}").buildAndExpand(courseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(courseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCourseDTO> findCourseById(@PathVariable Long id){
        ResponseCourseDTO courseDTO = courseService.findCourseById(id);
        return ResponseEntity.ok().body(courseDTO);
    }

    @GetMapping()
    public ResponseEntity<Page<ResponseCourseDTO>> coursesList(Pageable pageable){
        Page<ResponseCourseDTO> coursesDTOS = courseService.findAll(pageable);
        return ResponseEntity.ok().body(coursesDTOS);
    }

    @GetMapping("/by-instructor")
    public ResponseEntity<Page<ResponseCourseDTO>> findCourseByInstructorName(@RequestParam String name,
                                                                              Pageable pageable){
        Page<ResponseCourseDTO> coursesDTOS = courseService.findByInstructorName(name, pageable);
        return ResponseEntity.ok().body(coursesDTOS);
    }

//    @GetMapping("/instructor/{id}")
//    public ResponseEntity<ResponseCourseDTO> getByInstructorId()
}
