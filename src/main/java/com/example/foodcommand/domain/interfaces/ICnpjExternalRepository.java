package com.example.foodcommand.domain.interfaces;

import com.example.foodcommand.application.DTOs.ConsultaCnpjResponse;

public interface ICnpjExternalRepository {

    ConsultaCnpjResponse consultarCnpj(String cnpj);

}
