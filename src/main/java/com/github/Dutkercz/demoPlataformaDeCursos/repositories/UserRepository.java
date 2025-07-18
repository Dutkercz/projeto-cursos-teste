package com.github.Dutkercz.demoPlataformaDeCursos.repositories;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
