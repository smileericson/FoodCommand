package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.Cardapio;

import java.math.BigDecimal;

public record CardapioResponse(long id, String nome, String descricao, BigDecimal preco, EnumStatusCardapio statusCardapio) {

    public CardapioResponse(Cardapio cardapioEntidade){
        this(
                cardapioEntidade.getId(),
                cardapioEntidade.getNome(),
                cardapioEntidade.getDescricao(),
                cardapioEntidade.getPreco(),
                cardapioEntidade.getStatus()
        );
    }
}
