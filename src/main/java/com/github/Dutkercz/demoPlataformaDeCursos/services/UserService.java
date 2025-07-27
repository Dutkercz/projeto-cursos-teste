package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.user.UserRequestDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.user.UserResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.user.UserUpdateDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public UserResponseDTO saveEntity(@Valid UserRequestDTO userRequestDTO) {
        String passwordEncoded = encoder.encode(userRequestDTO.password());

        if (userRequestDTO.role().equalsIgnoreCase("aluno")){
            Student student = new Student(
                    null,
                    userRequestDTO.name(),
                    passwordEncoded,
                    userRequestDTO.email(),
                    userRequestDTO.cpf(),
                    LocalDateTime.now(),
                    true);

            return new UserResponseDTO(userRepository.save(student));

        } else if (userRequestDTO.role().equalsIgnoreCase("instrutor")) {
            Instructor instructor = new Instructor(
                    null,
                    userRequestDTO.name(),
                    passwordEncoded,
                    userRequestDTO.email(),
                    userRequestDTO.cpf(),
                    true,
                    LocalDateTime.now()
                    );
            return new UserResponseDTO(userRepository.save(instructor));

        } else {
            throw new IllegalArgumentException("Role INVÁLIDO! Use 'Aluno' ou 'Instrutor'");
        }
    }

    public void deleteUser(User user) {
        user.setInactive();
        userRepository.save(user);
    }

    @Transactional
    public UserResponseDTO updateUser(@Valid UserUpdateDTO updateDTO, User user) {
        user = userVerification.validateUser(user.getEmail()) ;
        user.update(updateDTO);
        return new UserResponseDTO(user);
    }
}
