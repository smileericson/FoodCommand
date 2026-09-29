package com.example.foodcommand.controllers;

import com.example.foodcommand.DTOs.LoginRequest;
import com.example.foodcommand.DTOs.LoginResponse;
import com.example.foodcommand.repository.UsuarioRepository;
import com.example.foodcommand.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
// Informa ao Spring que esta classe é um controller REST.
// Ela recebe requisições HTTP e devolve respostas HTTP.
@RequestMapping("/auth")
// Define o caminho base das rotas deste controller.
// Todas as rotas aqui começam com /auth.
@Tag(description = "Controller de autenticação!",name="Autenticação")
public class AuthController {

    @Autowired
    // Injeta o serviço responsável pela geração dos tokens JWT.
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    // Injeta o repositório utilizado para consultar os usuários.

    @PostMapping("/login")
    // Define que este método atende requisições HTTP POST
    // no caminho /auth/login.
    @Operation(description = "Metodo de login", summary = "Autenticação de usuários")
    // Documenta a operação no Swagger.
    // summary define o resumo e description apresenta a descrição.
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest){
        // Recebe os dados do login e devolve uma resposta HTTP.
        // ResponseEntity<?> permite retornar respostas com diferentes
        // tipos de corpo, conforme o resultado da operação.



        if (usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())){
            // Verifica no banco de dados se existe um usuário
            // com o email e a senha recebidos na requisição.

            var token = tokenService.gerarToken(loginRequest.email());
            return ResponseEntity.ok(new LoginResponse(token));
            // ResponseEntity Retorna HTTP 200 OK com o token dentro
            // do objeto LoginResponse.

        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
        // Se não encontrar usuário com email e senha
        // correspondentes, retorna HTTP 401 Unauthorized,
        // sem corpo na resposta.
    }
}
