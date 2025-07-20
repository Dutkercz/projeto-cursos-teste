package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.CourseRepository;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserVerification userVerification;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository, UserVerification userVerification, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.userVerification = userVerification;
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public ResponseCourseDTO createNewCourse(RequestCourseDTO requestDTO, String userEmail){
        User user = userVerification.validateUser(userEmail);
        if (user instanceof Instructor){
            Instructor instructor = instructorRepository.getReferenceById(user.getId());
            Course course = courseRepository.save(new Course(null, requestDTO.title(), requestDTO.description(),
                    requestDTO.category(), instructor));
            return new ResponseCourseDTO(course);
        }else {
            throw new AccessDeniedException("Apenas instrutores podem cadastar cursos");
        }
    }

    public Page<ResponseCourseDTO> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(ResponseCourseDTO::new);
    }

    public ResponseCourseDTO findCourseById(Long id) {
        return new ResponseCourseDTO(courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso Id " + id + " não encontrado!")));
    }

    public Page<ResponseCourseDTO> findByInstructorName(String name, Pageable pageable) {
        return courseRepository.findAllByInstructorNameContainingIgnoreCase(name, pageable)
                .map(ResponseCourseDTO::new);
    }
}
