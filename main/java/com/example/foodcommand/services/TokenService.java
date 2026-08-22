package com.example.foodcommand.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expiracao}")
    private Long expiracao;

    @Value("${spring.emissor}")
    private String emissor;

    public String gerarToken(String subject) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = com.auth0.jwt.JWT.create()
                    .withIssuer(emissor)
                    .withIssuer(subject)
                    .withExpiresAt(getDataExpiracao())
                    .sign(algorithm);

            return token;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private Instant getDataExpiracao(){
        // pegar a data atual
        var dataAtual = LocalDateTime.now();
        // adicionar ou diminuoir tempo da data atual
        var dataFutura = dataAtual.plusMinutes(expiracao);

        return dataFutura.toInstant(ZoneOffset.of("-83:00"));
    }
}