package com.example.foodcommand.presentation;


import com.example.foodcommand.application.DTOs.EnderecoResponse;
import com.example.foodcommand.application.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {


    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{cep}")
    public ResponseEntity<?> buscarEndereco(@PathVariable String cep) {
        // Se o CEP não existir ou estiver em formato errado, o service lança uma exceção,
        // eu pego aqui e devolvo 400 com a mensagem "CEP inválido!" em vez do erro 500 genérico.
        try {                                                                // NOVO
            return ResponseEntity.ok(enderecoService.BuscarEndereco(cep));
        } catch (Exception e) {                                              // NOVO
            return ResponseEntity.badRequest().body("CEP inválido!");        // NOVO
        }
    }

}