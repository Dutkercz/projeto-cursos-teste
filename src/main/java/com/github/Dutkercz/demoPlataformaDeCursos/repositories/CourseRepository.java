package com.github.Dutkercz.demoPlataformaDeCursos.repositories;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
