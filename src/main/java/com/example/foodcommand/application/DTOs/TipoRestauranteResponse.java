package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.TipoRestaurante;

public record TipoRestauranteResponse(long id, String nome) {

    public TipoRestauranteResponse (TipoRestaurante tipoRestauranteEntidade){
        this(
                tipoRestauranteEntidade.getId(),
                tipoRestauranteEntidade.getNome()
        );
    }
}
