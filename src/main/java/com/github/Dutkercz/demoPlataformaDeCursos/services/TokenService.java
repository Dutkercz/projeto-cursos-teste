package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;

public class TokenService {

    @Value("${algorithm}")
    private static String ALGORITHM_KEY;


    public String createToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(ALGORITHM_KEY);
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer("Courses-Online-Project")
                .withExpiresAt(Instant.from(LocalDateTime.now().plusHours(2)))
                .sign(algorithm);
    }

    public String getSubject(String token){
        Algorithm algorithm = Algorithm.HMAC256(ALGORITHM_KEY);
        return JWT.require(algorithm)
                .withIssuer("Courses-Online-Project")
                .build()
                .verify(token)
                .getSubject();
    }
}
