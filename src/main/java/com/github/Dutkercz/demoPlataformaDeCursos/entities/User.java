package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import com.github.Dutkercz.demoPlataformaDeCursos.dtos.EntityUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;

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
    private Boolean is_Active;

    public User (){
    }

    public User(Long id, String name, String password, String email, String cpf, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.cpf = cpf;
        this.is_Active = isActive;
    }

    public void update(@Valid EntityUpdateDTO updateDTO) {
        if (updateDTO.name() != null && !updateDTO.name().isBlank()){
            this.name = updateDTO.name();
        }
        if (updateDTO.email() != null && !updateDTO.email().isBlank()){
            this.email = updateDTO.email();
        }
        if (updateDTO.password() != null && !updateDTO.password().isBlank()){
            this.email = updateDTO.email();
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

    public Boolean getIs_Active() {
        return is_Active;
    }

    public void setInactive() {
        this.is_Active = false;
    }
}
