package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityRequestDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional
    public EntityResponseDTO saveEntity(@Valid EntityRequestDTO entityRequestDTO) {
        encoder.encode(entityRequestDTO.password());

        if (entityRequestDTO.role().equalsIgnoreCase("aluno")){
            Student student = new Student(null, entityRequestDTO.name(),
                    entityRequestDTO.password(), entityRequestDTO.email(),
                    entityRequestDTO.cpf());

            return new EntityResponseDTO(userRepository.save(student));

        } else if (entityRequestDTO.role().equalsIgnoreCase("instrutor")) {
            Instructor instructor = new Instructor(null, entityRequestDTO.name(),
                    entityRequestDTO.password(), entityRequestDTO.email(),
                    entityRequestDTO.cpf());
            return new EntityResponseDTO(userRepository.save(instructor));

        } else {
            throw new IllegalArgumentException("Role INVÁLIDO! Use 'Aluno' ou 'Instrutor'");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}
