package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.student.StudentDetails;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.student.StudentResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.services.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
@SecurityRequirement(name = "bearer-key")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> findAllStudents(@AuthenticationPrincipal User user,
                                                                    Pageable pageable){
        Page<StudentResponseDTO> students = studentService.findByActive(user, pageable);
        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/courses")
    public ResponseEntity<Page<ResponseCourseDTO>> findStudentCourse(@AuthenticationPrincipal User user,
                                                                     Pageable pageable){
        Page<ResponseCourseDTO> courseDTOS = studentService.findStudentCourses(user, pageable);
        return ResponseEntity.ok().body(courseDTOS);
    }

    @GetMapping("/me")
    public ResponseEntity<StudentDetails> studentDetails(@AuthenticationPrincipal User user){
        StudentDetails studentDetails = studentService.findById(user);
        return ResponseEntity.ok().body(studentDetails);
    }
}
