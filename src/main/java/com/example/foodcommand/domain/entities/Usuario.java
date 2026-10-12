package com.example.foodcommand.domain.entities;

import com.example.foodcommand.application.DTOs.CriarAdminRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

// Lombok: As anotações do Lombok geram automaticamente códigos repetitivos,
// data: Gera automaticamente os métodos getters e setters,
// NoArgsConstructor: Cria automaticamente um construtor sem parâmetros.
//@AllArgsConstructor: Cria automaticamente um construtor com todos os atributos da classe.

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    //atributos
    public String nome;
    public String cpf;
    public String senha;
    public String email;

    private String role = "ROLE_USER";

    public EnumStatusUsuario status =EnumStatusUsuario.ATIVO;


    //construtor que pega as informações
    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setCpf(criarAdminRequest.cpf());
        this.setNome(criarAdminRequest.nome());
        this.setSenha(criarAdminRequest.senha());
        this.setEmail(criarAdminRequest.email());
        this.setRole("ROLE_ADMIN");

    }
}