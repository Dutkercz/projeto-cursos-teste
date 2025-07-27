package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.user.UserRequestDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.user.UserResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.user.UserUpdateDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.Instructor;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDTO userRequestDTO,
                                      UriComponentsBuilder builder){
        UserResponseDTO userResponseDTO = userService.saveEntity(userRequestDTO);
        URI responseUri = builder.path("/user/{id}").buildAndExpand(userResponseDTO.id()).toUri();
        return ResponseEntity.created(responseUri).body(userResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getDetailTeste(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body("Usuario logado: " + user.getName() + "\ntipo: " +
                (user instanceof Instructor ? "Instrutor" : "Aluno"));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserUpdateDTO updateDTO,
                                                      @AuthenticationPrincipal User user){
        UserResponseDTO userResponseDTO = userService.updateUser(updateDTO, user);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLoggedUser(@AuthenticationPrincipal User user){
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
