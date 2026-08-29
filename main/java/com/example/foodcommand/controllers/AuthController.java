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
@RequestMapping("/auth")
@Tag(description = "Controller de autenticação!",name="Autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(description = "Metodo de login", summary = "Autenticação de usuários")
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest){



        if (usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())){
            // Gerar o token
            var token = tokenService.gerarToken(loginRequest.email());
            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
    }
}
