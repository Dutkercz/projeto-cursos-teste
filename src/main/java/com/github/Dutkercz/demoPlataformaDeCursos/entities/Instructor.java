package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User{

    public Instructor() {
    }

    public Instructor(Long id, String name, String password, String email, String cpf, Boolean isActive, LocalDateTime registerDate) {
        super(id, name, password, email, cpf, isActive, registerDate);
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
        return super.getIsActive();
    }
}
