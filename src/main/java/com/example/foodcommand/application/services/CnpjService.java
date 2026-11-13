package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.ConsultaCnpjResponse;
import com.example.foodcommand.domain.interfaces.ICnpjExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CnpjService {

    @Autowired
    private ICnpjExternalRepository cnpjExternalRepository;

    public ConsultaCnpjResponse buscarEmpresaPorCnpj(String cnpj) {

        var dadosEmpresa = cnpjExternalRepository.consultarCnpj(cnpj);

        if (dadosEmpresa == null) {
            throw new RuntimeException("CNPJ não encontrado na Receita Federal");
        }

        return dadosEmpresa;
    }
}
