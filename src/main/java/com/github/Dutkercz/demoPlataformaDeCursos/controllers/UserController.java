package com.github.Dutkercz.demoPlataformaDeCursos.controllers;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityRequestDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityResponseDTO;
import com.github.Dutkercz.demoPlataformaDeCursos.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("user")
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

}
