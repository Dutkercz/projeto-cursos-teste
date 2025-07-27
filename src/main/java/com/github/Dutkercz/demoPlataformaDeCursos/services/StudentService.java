package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.student.StudentDetails;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.student.StudentResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
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
    private final UserVerification userVerification;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, UserVerification userVerification) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.userVerification = userVerification;
    }

    /// retorna os estudent ativos
    public Page<StudentResponseDTO> findByActive(User user, Pageable pageable){
        if (!(user instanceof Instructor)){
            throw new AccessDeniedException("Você não tem autorização para obter a lista completa de alunos");
        }
        return studentRepository.findAllByIsActiveTrue(pageable).map(StudentResponseDTO::new);
    }

    /***
     * Encontra os cursos em que o aluno(Student) está matriculado
     * @param user deve ser obrigatoriamente uma instancia de Student, caso o contrario ja retorna uma except
     * @param pageable mantem a paginação do Json
     * @return retorna um DTO com a lista dos cursos desse Student
     */
    public Page<ResponseCourseDTO> findStudentCourses(User user, Pageable pageable) {
        if (!(user instanceof Student)){
            throw new AccessDeniedException("Hey, você não é um aluno!");
        }
        return courseRepository.findByStudentsId(user.getId(), pageable).map(ResponseCourseDTO::new);
    }

    public StudentDetails findById(User user) {
        if (!(user instanceof Student)){
            throw new AccessDeniedException("Para informações de instrutores acesse a sua contra de instrutor");
        }
        Student student = (Student) userVerification.validateUser(user.getEmail());
        return new StudentDetails(student);
    }
}