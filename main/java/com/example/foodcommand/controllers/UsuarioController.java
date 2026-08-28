package com.example.foodcommand.controllers;

import com.example.foodcommand.entities.Usuario;
import com.example.foodcommand.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
//todos tem que ter tag e explicar o que faz
@Tag(name="Usuarios", description = "Grupo de APIs responsavel por controlar a estrutura e cosulta de usuário do sistema!")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    // vai ficar ao lado do usuario vai ficar tipo um titulo,a description é a mais importante vai descrever a regra de negocio.
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a consulta de todos os usuarios sem filtro")
    public ResponseEntity<?> listarTodos(){

        List<Usuario> usuarios = List.of(new Usuario(1L,
                "Ericson",
                "08319706939",
                "123456",
                "smileericson@gmail.com"));

        return ResponseEntity.ok(usuarioRepository.findAll());

    }

    //acesso, saida, nome , entrada

    @PostMapping
    // responsestatus - Ele transforma nosso status comum em 201
    @ResponseStatus(HttpStatus.CREATED)
    // copiar e altera para criação.
    @Operation(summary = "Metodo de cosulta de lista de usuários",description = "Metodo responsavel em efetuar a criação de novos usuarios")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){

        var usuarioBanco =  usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);
    }

}
