package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.StudentResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> findAllStudents(Pageable pageable){
        Page<StudentResponseDTO> students = studentService.findByActive(pageable);
        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/courses")
    public ResponseEntity<Page<ResponseCourseDTO>> findStudentCourse(@AuthenticationPrincipal User user,
                                                                     Pageable pageable){
        Page<ResponseCourseDTO> courseDTOS = studentService.findStudentCourses(user, pageable);
        return ResponseEntity.ok().body(courseDTOS);
    }
}
