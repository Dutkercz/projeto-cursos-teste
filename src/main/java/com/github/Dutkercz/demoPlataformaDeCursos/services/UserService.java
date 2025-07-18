package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityRequestDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Student;
import com.github.Dutkercz.demoPlataformaDeCursos.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public EntityResponseDTO saveEntity(@Valid EntityRequestDTO entityRequestDTO) {
        if (entityRequestDTO.role().equalsIgnoreCase("aluno")){
            Student student = new Student(null, entityRequestDTO.name(),
                    entityRequestDTO.password(), entityRequestDTO.email(),
                    entityRequestDTO.cpf());

            return new EntityResponseDTO(userRepository.save(student));

        }else {
            Instructor instructor = new Instructor(null, entityRequestDTO.name(),
                    entityRequestDTO.password(), entityRequestDTO.email(),
                    entityRequestDTO.cpf());
            return new EntityResponseDTO(userRepository.save(instructor));
        }
    }

}
