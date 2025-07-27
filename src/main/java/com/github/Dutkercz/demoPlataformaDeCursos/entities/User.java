package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.user.UserUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Table(name = "db_user")
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String cpf;
    private String email;
    private Boolean isActive;
    private LocalDateTime registerDate;

    public User (){
    }

    public User(Long id, String name, String password, String email, String cpf, Boolean isActive, LocalDateTime registerDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.cpf = cpf;
        this.isActive = isActive;
        this.registerDate = registerDate;
    }

    public void update(@Valid UserUpdateDTO updateDTO) {
        if (updateDTO.name() != null && !updateDTO.name().isBlank()){
            this.name = updateDTO.name();
        }
        if (updateDTO.email() != null && !updateDTO.email().isBlank()){
            this.email = updateDTO.email();
        }
        if (updateDTO.password() != null && !updateDTO.password().isBlank()){
            this.password = updateDTO.password();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setInactive() {
        this.isActive = false;
    }
}
