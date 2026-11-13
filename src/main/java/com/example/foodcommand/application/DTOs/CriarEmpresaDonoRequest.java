package com.example.foodcommand.application.DTOs;

public record CriarEmpresaDonoRequest(String nome, String email, String senha, String cpf,
                                      String razaoSocial, String cnpj, String nomeFantasia,
                                      String secretKey) {
}
