package com.example.foodcommand.application.DTOs;

public record ConsultaCnpjResponse(String razaoSocial, String nomeFantasia, String cep, String logradouro,
                                   String bairro, String cidade, String uf) {
}
