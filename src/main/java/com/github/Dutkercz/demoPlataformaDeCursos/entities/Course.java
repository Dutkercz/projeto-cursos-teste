package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String category;

    @ManyToOne
    private Instructor instructor;

    @ManyToMany
    @JoinTable(
            name = "db_course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

    public Course(Long id, String title, String description, String category, Instructor instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.instructor = instructor;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public void removeStudent(Student student){
        this.students.remove(student);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public List<Student> getStudents() {
        return students;
    }
}
