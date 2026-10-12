package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.EnumStatusCardapio;


public record AtualizarStatusCardapio(EnumStatusCardapio statusCardapio) {
    // Declara um record chamado AtualizarStatusRequest.
    // O record é uma estrutura do Java utilizada para representar
    // dados de forma concisa.
    // O atributo status é do tipo EnumStatusMenu.
    // O Java gera automaticamente o construtor, o método de
    // acesso status(), além de equals(), hashCode() e toString().
}
