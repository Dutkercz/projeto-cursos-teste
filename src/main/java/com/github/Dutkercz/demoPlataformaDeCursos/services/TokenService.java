package com.github.Dutkercz.demoPlataformaDeCursos.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
        try {

            Algorithm algorithm = Algorithm.HMAC256(ALGORITHM_KEY);
            return JWT.create()
                    .withSubject(user.getEmail())
                    .withIssuer("Courses-Online-Project")
                    .withExpiresAt(Instant.now().plus(2L, ChronoUnit.HOURS))
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    public String getSubject(String token){
        try {

            Algorithm algorithm = Algorithm.HMAC256(ALGORITHM_KEY);
            return JWT.require(algorithm)
                    .withIssuer("Courses-Online-Project")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado!");
        }
    }
}
