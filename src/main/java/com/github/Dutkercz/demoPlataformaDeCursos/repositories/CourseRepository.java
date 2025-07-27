package com.github.Dutkercz.demoPlataformaDeCursos.repositories;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.Course;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByInstructorNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Course> findByStudentsId(Long id, Pageable pageable);

    Page<Course> findAllByTitleContainingIgnoreCase(String courseName, Pageable pageable);

    @Query("""
            SELECT s FROM Course c
            JOIN c.students s
            WHERE c.id = :courseId
            """)
    Page<Student> findAllStudentsById(@Param("courseId")Long courseId, Pageable pageable);
}
