package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.Cardapio;
import com.example.foodcommand.domain.entities.EnumStatusCardapio;

public record CardapioResponse(long id, String nome, String descricao, String preco, EnumStatusCardapio statusCardapio) {

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
