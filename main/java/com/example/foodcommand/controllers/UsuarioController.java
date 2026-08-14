package com.example.foodcommand.controllers;

import com.example.foodcommand.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        List<Usuario> usuarios = List.of(new Usuario(1L,
                "Ericson",
                "08319706939",
                "123456",
                "smileericson@gmail.com"));
        return ResponseEntity.ok(usuarios);

    }
}
