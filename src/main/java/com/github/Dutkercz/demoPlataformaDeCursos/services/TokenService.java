package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${alg.pw}")
    private String ALGORITHM_KEY;
    //descobri que @Value não funciona com static, depois de 1h procurando esse erro -.-

    public String createToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(ALGORITHM_KEY);
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer("Courses-Online-Project")
                .withExpiresAt(Instant.now().plus(2L, ChronoUnit.HOURS))
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
