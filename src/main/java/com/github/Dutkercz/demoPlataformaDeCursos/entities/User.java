package com.github.Dutkercz.demoPlataformaDeCursos.entities;

import jakarta.persistence.*;
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

    public User (){
    }

    public User(Long id, String name, String password, String email, String cpf) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.cpf = cpf;
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

}
