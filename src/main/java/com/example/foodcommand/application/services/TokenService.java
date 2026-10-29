package com.example.foodcommand.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.foodcommand.application.DTOs.LoginRequest;
import com.example.foodcommand.application.DTOs.UsuarioLogado;
import com.example.foodcommand.domain.entities.Token;
import com.example.foodcommand.domain.entities.Usuario;
import com.example.foodcommand.domain.repository.TokenRepository;
import com.example.foodcommand.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
//anotação do Spring Boot que indica que a classe contém a lógica de negócio da aplicação
public class TokenService {

    // Obtém o segredo
    @Value("${spring.secret}")
    private String secret;

    // Obtém o tempo de expiração
    @Value("${spring.expiracao}")
    private Long expiracao;

    // Obtém o emissor do token
    @Value("${spring.emissor}")
    private String emissor;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;


    // Método responsável por gerar um token JWT , recebe o parametro subject e identifica o asunto
    // Define o algoritmo HMAC256
    // Cria o JWT e configura suas informações
    public String gerarToken(LoginRequest loginRequestDto) {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            var usuario = usuarioRepository.findAll()
                    .stream().filter(u->u.getEmail().equals(loginRequestDto.email())).findFirst().orElse(null);


            String token = JWT.create()
                    // Define quem emitiu o token.// Define o subject, ou seja, a identificação associada ao token
                    // Define a data e hora em que o token irá expirar.// Assina o token utilizando o algoritmo configurado
                    .withIssuer(emissor)
                    .withSubject(loginRequestDto.email())
                    .withExpiresAt(getDataExpiracao())
                    .sign(algorithm);

            tokenRepository.save(new Token(usuario, token));


            return token;
    }
    // Método responsável por verificar e validar um token JWT
    // recebe o token como parametro e retorna jwt
    // cria o algoritmo hmac utilizando o mesmo segredo
    // cria o verificador jwt utilitando o alogritmo e emissor esperado
    // verifica o token e retorna suas informações.

    public UsuarioLogado vericarToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();

        verificador.verify(token);

        var tokenBanco = tokenRepository.findByToken(token).orElse(null);

        if (tokenBanco == null) {
            throw new IllegalArgumentException("token Invalido!");
        }

        return new UsuarioLogado(tokenBanco.getUsuario());
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