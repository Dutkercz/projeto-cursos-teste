package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.RequestUpdateCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.mapper.CourseMapper;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.others.ResponseEnrollDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.student.StudentResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.CourseRepository;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserVerification userVerification;
    private final InstructorRepository instructorRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, UserVerification userVerification, InstructorRepository instructorRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.userVerification = userVerification;
        this.instructorRepository = instructorRepository;
        this.courseMapper = courseMapper;
    }

    @Transactional
    public ResponseCourseDTO createNewCourse(RequestCourseDTO requestDTO, String userEmail){
        User user = userVerification.validateUser(userEmail);
        if (user instanceof Instructor){
            Instructor instructor = instructorRepository.getReferenceById(user.getId());
            Course course = courseRepository.save(courseMapper.requestCourseToEntity(requestDTO, instructor));
            return courseMapper.courseToDTO(course);
        }else {
            throw new AccessDeniedException("Apenas instrutores podem cadastar cursos");
        }
    }

    public Page<ResponseCourseDTO> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::courseToDTO);
    }

    public ResponseCourseDTO findCourseById(Long id) {
        return courseMapper.courseToDTO(courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso Id " + id + " não encontrado!")));
    }

    public Page<ResponseCourseDTO> findByInstructorName(String name, Pageable pageable) {
        return courseRepository.findAllByInstructorNameContainingIgnoreCase(name, pageable)
                .map(courseMapper::courseToDTO);
    }

    /***
     * Adicionar um novo aluno à lista de students da Entidade Courses
     * @param user usuario logado, deve ser um Student para poder se matricular, caso contrario retona uma except
     * @param courseId id do curso escolhido
     * @return retorna um resumo da matricula (enroll)
     */
    @Transactional
    public ResponseEnrollDTO enrollStudent(User user, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Curso de id "+courseId+" não encontrado"));

        if (!(user instanceof Student)) {
            throw new AccessDeniedException("Apenas alunos podem se matricular");
        }
        Student student = (Student) userVerification.validateUser(user.getEmail());
        course.addStudent(student);
        courseRepository.save(course);
        return courseMapper.courseToEnroll(course, student);
    }

    @Transactional
    public ResponseCourseDTO updateCourse(User user, Long courseId, RequestUpdateCourseDTO updateCourseDTO) {
        Instructor instructor = (Instructor) userVerification.validateUser(user.getEmail());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Curso de id " + courseId + " não encontrado"));
        if (!Objects.equals(course.getInstructor().getId(), instructor.getId())){
            throw new AccessDeniedException("Você não tem permissão para modificar este curso!");
        }
        course.update(updateCourseDTO);
        return courseMapper.courseToDTO(course);
    }

    @Transactional
    public void deleteCourse(User user, Long courseId) {
        if (user instanceof Student){
            throw new AccessDeniedException("Área restrita para instrutores");
        }
        Instructor instructor = (Instructor) userVerification.validateUser(user.getEmail());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Curso de id " + courseId + " não encontrado"));
        if (!Objects.equals(course.getInstructor().getId(), instructor.getId())){
            throw new AccessDeniedException("Você não tem permissão para modificar este curso!");
        }
        courseRepository.deleteById(course.getId());
    }

    public Page<ResponseCourseDTO> findByCourseName(String courseTitle, Pageable pageable) {
        return courseRepository.findAllByTitleContainingIgnoreCase(courseTitle, pageable)
                .map(courseMapper::courseToDTO);
    }

    public Page<StudentResponseDTO> findStudentsCourse(User user, Long courseId, Pageable pageable) {
        if (user instanceof Student){
            throw new AccessDeniedException("Você não tem permissão para obter informações desse curso!");
        }
        Instructor instructor = (Instructor) userVerification.validateUser(user.getEmail());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Curso de id " + courseId + " não encontrado"));
        if (!Objects.equals(course.getInstructor().getId(), instructor.getId())){
            throw new AccessDeniedException("Você não tem permissão para obter informações desse curso!");
        }
        return courseRepository.findAllStudentsById(courseId, pageable).map(StudentResponseDTO::new);
    }

    public Page<ResponseCourseDTO> findInstructorCourses(User user, Pageable pageable) {
        if (user instanceof Student){
            throw new AccessDeniedException("Área restrita para instrutores");
        }
        Instructor instructor = (Instructor) userVerification.validateUser(user.getEmail());
        return courseRepository.findAllByInstructorId(instructor.getId(), pageable)
                .map(ResponseCourseDTO::new);
    }
}
