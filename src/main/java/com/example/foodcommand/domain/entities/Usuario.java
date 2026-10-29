package com.example.foodcommand.domain.entities;

import com.example.foodcommand.application.DTOs.CriarAdminRequest;
import com.example.foodcommand.application.DTOs.EnumStatusUsuario;
import com.example.foodcommand.application.DTOs.UsuarioLogado;
import com.example.foodcommand.application.DTOs.UsuarioRequest;
import com.example.foodcommand.domain.valueobject.CPF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


// Cada objeto dessa classe vira uma linha da tabela.
// Lombok: As anotações do Lombok geram automaticamente códigos repetitivos,
// data: Gera automaticamente os métodos getters e setters,
// NoArgsConstructor: Cria automaticamente um construtor sem parâmetros.
//@AllArgsConstructor: Cria automaticamente um construtor com todos os atributos da classe.

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    //atributos
    public String nome;
    @Embedded
    public CPF cpf;
    public String senha;
    public String email;

    private String role = "ROLE_USER";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    public EnumStatusUsuario status =EnumStatusUsuario.ATIVO;


    //construtor que pega as informações
    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setCpf(new CPF(criarAdminRequest.cpf()));
        this.setNome(criarAdminRequest.nome());
        this.setSenha(criarAdminRequest.senha());
        this.setEmail(criarAdminRequest.email());
        this.setRole("ROLE_ADMIN");

    }

    public Usuario(UsuarioRequest usuario) {
        this.setCpf(new CPF(usuario.cpf()));
        this.setNome(usuario.nome());
        this.setSenha(usuario.senha());
        this.setEmail(usuario.email());
        this.setRole("ROLE_USER");
    }

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}