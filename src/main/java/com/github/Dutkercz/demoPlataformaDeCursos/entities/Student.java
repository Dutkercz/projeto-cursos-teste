package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{

    public Student() {
    }

    public Student(Long id, String name, String password, String email, String cpf) {
        super(id, name, password, email, cpf);
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
