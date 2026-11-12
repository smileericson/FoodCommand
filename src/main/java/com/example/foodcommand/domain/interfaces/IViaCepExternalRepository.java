package com.example.foodcommand.domain.interfaces;

import com.example.foodcommand.infra.dto.ViaCepResponse;

public interface IViaCepExternalRepository {

    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);


}