package com.example.foodcommand.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.foodcommand.application.DTOs.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
//anotação do Spring Boot que indica que a classe contém a lógica de negócio da aplicação
public class TokenService {

    @Value("${spring.secret}")
    // Obtém o segredo
    private String secret;

    @Value("${spring.expiracao}")
    // Obtém o tempo de expiração
    private Long expiracao;

    @Value("${spring.emissor}")
    // Obtém o emissor do token
    private String emissor;

    // Método responsável por gerar um token JWT , recebe o parametro subject e identifica o asunto
    // Define o algoritmo HMAC256
    // Cria o JWT e configura suas informações
    public String gerarToken(LoginRequest loginRequestDto) {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()

                    .withIssuer(emissor)
                    // Define quem emitiu o token
                    .withSubject(loginRequestDto.email())
                    // Define o subject, ou seja, a identificação associada ao token
                    .withExpiresAt(getDataExpiracao())
                    // Define a data e hora em que o token irá expirar
                    .sign(algorithm);
                    // Assina o token utilizando o algoritmo configurado

            return token;
    }
    // Método responsável por verificar e validar um token JWT
    // recebe o token como parametro e retorna jwt
    // cria o algoritmo hmac utilizando o mesmo segredo
    // cria o verificador jwt utilitando o alogritmo e emissor esperado
    // verifica o token e retorna suas informações.

    public DecodedJWT vericarToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();

        return verificador.verify(token);
    }
    // Método privado responsável por calcular a data de expiração do token
    // pega a data atual, depois a data fitura que é igual a data atual com o tempo de expiração.
    // por fim converte a data futura para um instante utilziando o fuso, retornando data e hora no formato instant
    private Instant getDataExpiracao(){

        var dataAtual = LocalDateTime.now();
        var dataFutura = dataAtual.plusMinutes(expiracao);

        return dataFutura.toInstant(ZoneOffset.of("-03:00"));
    }
}