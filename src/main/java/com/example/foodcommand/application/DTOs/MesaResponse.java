package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.EnumStatusMesa;
import com.example.foodcommand.domain.entities.Mesa;

public record MesaResponse(long id, int numeroMesa, EnumStatusMesa status) {

    public MesaResponse(Mesa mesaEntidade){

        this(
                mesaEntidade.getId(),
                mesaEntidade.getNumeroMesa(),
                mesaEntidade.getStatus()
        );
    }
}
