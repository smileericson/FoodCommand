package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.Mesa;

public record MesaResponse(long id, int numeroMesa, EnumStatusMesa statusMesa) {

    public MesaResponse(Mesa mesaEntidade){

        this(
                mesaEntidade.getId(),
                mesaEntidade.getNumeroMesa(),
                mesaEntidade.getStatusMesa()
        );
    }
}
