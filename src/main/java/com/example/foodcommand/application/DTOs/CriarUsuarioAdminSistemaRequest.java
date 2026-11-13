package com.example.foodcommand.application.DTOs;

public record CriarUsuarioAdminSistemaRequest(String nome, String email, String senha, String cpf, String secretKey) {
}
