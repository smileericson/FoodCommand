package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.EnderecoResponse;
import com.example.foodcommand.domain.interfaces.IViaCepExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private IViaCepExternalRepository viaCepExternalRepository;


    public EnderecoResponse BuscarEndereco(String cep) {

        var enderecoCompleto =  viaCepExternalRepository.buscarEnderecoViaCepPorCep(cep);

        return new EnderecoResponse( enderecoCompleto.getCep(),
                enderecoCompleto.getLogradouro(),
                enderecoCompleto.getBairro(),
                enderecoCompleto.getLocalidade(),
                enderecoCompleto.getUf());


    }
}