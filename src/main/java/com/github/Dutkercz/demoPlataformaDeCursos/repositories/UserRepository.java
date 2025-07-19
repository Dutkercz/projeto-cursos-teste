package com.github.Dutkercz.demoPlataformaDeCursos.repositories;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findByEmail(String email);
}
