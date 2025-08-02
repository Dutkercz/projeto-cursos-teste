package com.github.Dutkercz.demoPlataformaDeCursos.dtos.mapper;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.RequestCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.RequestUpdateCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.course.ResponseCourseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.others.ResponseEnrollDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "instructor", source = "course.instructor.name")
    ResponseCourseDTO courseToDTO(Course course);

    @Mapping(target = "courseName", source = "course.title")
    @Mapping(target = "instructor", source = "course.instructor.name")
    @Mapping(target = "student", source = "student.name")
    ResponseEnrollDTO courseToEnroll(Course course, User student);

    @Mapping(target = "instructor", source = "instructor")
    Course requestCourseToEntity(RequestCourseDTO requestCourseDTO, Instructor instructor);

    Course updateCourseToEntity(RequestUpdateCourseDTO updateCourseDTO);

}
