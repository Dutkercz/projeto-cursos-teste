package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.CourseRepository;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.InstructorRepository;
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
}
