package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.StudentResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.CourseRepository;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Page<StudentResponseDTO> findByActive(Pageable pageable){
        return studentRepository.findAllByIsActiveTrue(pageable).map(StudentResponseDTO::new);
    }

    public Page<ResponseCourseDTO> findStudentCourses(User user, Pageable pageable) {
        if (!(user instanceof Student)){
            throw new AccessDeniedException("Hey, você não é um aluno!");
        }
        return courseRepository.findByStudentsId(user.getId(), pageable).map(ResponseCourseDTO::new);
    }
}