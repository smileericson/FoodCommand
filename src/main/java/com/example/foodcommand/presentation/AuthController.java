package com.example.foodcommand.presentation;

import com.example.foodcommand.application.DTOs.LoginRequest;
import com.example.foodcommand.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//1 - recebe a requição do http e retono resposta ao api
//2 - defini caminho base para endpoints deste controller
//3- agrupa o controller no swagger com o nome
//4 - injeta instancia automaticamente "usuarioservice"
//5 - define o endpoint
//6 - documentta o end point


@RestController
@RequestMapping("/auth")

@Tag(description = "Controller de autenticação!",name="Autenticação")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(description = "Metodo de login", summary = "Autenticação de usuários")

    // recebe a resquisição de post contendo os dados do login
    // envia os dados recebidos do login para o usuario service, service valida se estiver correto retorna o token
    //if verifica se a autenticação foi realizada com sucesso e retorna http 200
    //retur caso contrario, estiver incorretos retorna http 400

    public ResponseEntity<?>login(@RequestBody LoginRequest Request){


    var resultadoAutenticacaoToken = usuarioService.validarUsuarioAutenticadoRetornaToken(Request);

        if (resultadoAutenticacaoToken != null) {
            return ResponseEntity.ok(resultadoAutenticacaoToken);
        }
        return ResponseEntity.badRequest().body("Usuario ou senha inválido!");
    }
}
