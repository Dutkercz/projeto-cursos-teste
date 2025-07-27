package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.RequestUpdateCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.others.ResponseEnrollDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.student.StudentResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.services.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
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

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<ResponseEnrollDTO> enrollStudent(@AuthenticationPrincipal User user,
                                                           @PathVariable Long courseId){
        ResponseEnrollDTO enrollDTO = courseService.enrollStudent(user, courseId);
        return ResponseEntity.ok().body(enrollDTO);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@AuthenticationPrincipal User user, @PathVariable Long courseId,
                                          @RequestBody RequestUpdateCourseDTO updateCourseDTO){
        ResponseCourseDTO updatedCourseDTO = courseService.updateCourse(user, courseId, updateCourseDTO);
        return ResponseEntity.ok().body(updatedCourseDTO);
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
    public ResponseEntity<Page<ResponseCourseDTO>> findCourseByInstructorName(@RequestParam String instructorName,
                                                                              Pageable pageable){
        Page<ResponseCourseDTO> coursesDTOS = courseService.findByInstructorName(instructorName, pageable);
        return ResponseEntity.ok().body(coursesDTOS);
    }

    @GetMapping("/by-title")
    public ResponseEntity<Page<ResponseCourseDTO>> findCoursesByTitle(@RequestParam String courseTitle,
                                                                      Pageable pageable){
        Page<ResponseCourseDTO> responseCourseDTOS = courseService.findByCourseName(courseTitle, pageable);
        return ResponseEntity.ok().body(responseCourseDTOS);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<Page<StudentResponseDTO>> findStudentsCourse(@AuthenticationPrincipal User user,
                                                                       @PathVariable Long courseId, Pageable pageable){
        Page<StudentResponseDTO> studentResponseDTOS = courseService.findStudentsCourse(user, courseId, pageable);
        return ResponseEntity.ok().body(studentResponseDTOS);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<Page<ResponseCourseDTO>> findInstructorCourses(@AuthenticationPrincipal User user,
                                                                         Pageable pageable){
        Page<ResponseCourseDTO> courseDTOS = courseService.findInstructorCourses(user, pageable);
        return ResponseEntity.ok().body(courseDTOS);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@AuthenticationPrincipal User user, @PathVariable Long courseId){
        courseService.deleteCourse(user, courseId);
        return ResponseEntity.status(NO_CONTENT).body("Curso excluido!");
    }
}
