package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{

    @ManyToMany(mappedBy = "students")
    private List<Course> enrolledCourses = new ArrayList<>();

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Student() {
    }

    public Student(Long id, String name, String password, String email, String cpf, Boolean isActive) {
        super(id, name, password, email, cpf, isActive);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
