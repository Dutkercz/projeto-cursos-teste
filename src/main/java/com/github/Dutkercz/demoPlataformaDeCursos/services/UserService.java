package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityRequestDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityUpdateDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserVerification userVerification;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, UserVerification userVerification) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userVerification = userVerification;
    }

    @Transactional
    public EntityResponseDTO saveEntity(@Valid EntityRequestDTO entityRequestDTO) {
        String passwordEncoded = encoder.encode(entityRequestDTO.password());

        if (entityRequestDTO.role().equalsIgnoreCase("aluno")){
            Student student = new Student(null, entityRequestDTO.name(),
                    passwordEncoded, entityRequestDTO.email(),
                    entityRequestDTO.cpf(), true);

            return new EntityResponseDTO(userRepository.save(student));

        } else if (entityRequestDTO.role().equalsIgnoreCase("instrutor")) {
            Instructor instructor = new Instructor(null, entityRequestDTO.name(),
                    passwordEncoded, entityRequestDTO.email(),
                    entityRequestDTO.cpf(), true);
            return new EntityResponseDTO(userRepository.save(instructor));

        } else {
            throw new IllegalArgumentException("Role INVÁLIDO! Use 'Aluno' ou 'Instrutor'");
        }
    }

    public void deleteUser(User user) {
        user.setInactive();
        userRepository.save(user);
    }

    @Transactional
    public EntityResponseDTO updateUser(@Valid EntityUpdateDTO updateDTO, User user) {
        user = userVerification.validateUser(user.getEmail()) ;
        user.update(updateDTO);
        return new EntityResponseDTO(user);
    }
}
