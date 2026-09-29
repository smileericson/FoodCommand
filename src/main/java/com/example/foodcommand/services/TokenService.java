package com.example.foodcommand.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
//anotação do Spring Boot que indica que a classe contém a lógica de negócio da aplicação
public class TokenService {

    @Value("${spring.secret}")
    // Obtém o segredo definido na configuração spring.secret
    private String secret;

    @Value("${spring.expiracao}")
    // Obtém o tempo de expiração definido na configuração spring.expiracao
    private Long expiracao;

    @Value("${spring.emissor}")
    // Obtém o emissor do token definido na configuração spring.emissor
    private String emissor;

    public String gerarToken(String subject) {
        // Método responsável por gerar um token JWT
        // Recebe como parâmetro o subject, que identifica o assunto do token

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Define o algoritmo HMAC256 utilizando o segredo da aplicação
            String token = com.auth0.jwt.JWT.create()
                    // Cria o JWT e configura suas informações
                    .withIssuer(emissor)
                    // Define quem emitiu o token
                    .withSubject(subject)
                    // Define o subject, ou seja, a identificação associada ao token
                    .withExpiresAt(getDataExpiracao())
                    // Define a data e hora em que o token irá expirar
                    .sign(algorithm);
                    // Assina o token utilizando o algoritmo configurado

            return token;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
            // Captura uma exceção de execução ocorrida durante a geração
            // e lança novamente uma RuntimeException com a exceção recebida

        }
    }

    public DecodedJWT vericarToken(String token) throws JWTVerificationException {
        // Método responsável por verificar e validar um token JWT
        // Recebe o token como parâmetro e retorna um JWT decodificado

        Algorithm algorithm = Algorithm.HMAC256(secret);
        // Cria o algoritmo HMAC256 utilizando o mesmo segredo da geração

        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();
        // Cria o verificador JWT utilizando o algoritmo e o emissor esperado

        return verificador.verify(token);
        // Verifica o token e retorna suas informações decodificadas
        // Se a validação falhar, pode lançar JWTVerificationException

    }

    private Instant getDataExpiracao(){
        // Método privado responsável por calcular a data de expiração do token
        // É utilizado internamente pela classe

        var dataAtual = LocalDateTime.now();
        // pegar a data atual
        var dataFutura = dataAtual.plusMinutes(expiracao);
        // pegar a data atual

        return dataFutura.toInstant(ZoneOffset.of("-03:00"));
        // Converte a data futura para Instant utilizando o fuso -03:00
        // Retorna a data e hora no formato Instant
    }
}