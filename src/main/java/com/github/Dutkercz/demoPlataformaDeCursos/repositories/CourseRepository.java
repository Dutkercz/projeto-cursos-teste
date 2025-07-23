package com.github.Dutkercz.demoPlataformaDeCursos.repositories;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByInstructorNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Course> findByStudentsId(Long id, Pageable pageable);
}
