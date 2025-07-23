package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityRequestDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityUpdateDTO;
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
    public ResponseEntity<?> register(@Valid @RequestBody EntityRequestDTO entityRequestDTO,
                                      UriComponentsBuilder builder){
        EntityResponseDTO entityResponseDTO = userService.saveEntity(entityRequestDTO);
        URI responseUri = builder.path("/user/{id}").buildAndExpand(entityResponseDTO.id()).toUri();
        return ResponseEntity.created(responseUri).body(entityResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getDetailTeste(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body("Usuario logado " + user.getName());
    }

    @PutMapping
    public ResponseEntity<EntityResponseDTO> updateUser(@Valid @RequestBody EntityUpdateDTO updateDTO,
                                        @AuthenticationPrincipal User user){
        EntityResponseDTO entityResponseDTO = userService.updateUser(updateDTO, user);
        return ResponseEntity.ok().body(entityResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLoggedUser(@AuthenticationPrincipal User user){
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
