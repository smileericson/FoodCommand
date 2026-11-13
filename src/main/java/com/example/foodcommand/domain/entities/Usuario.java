package com.example.foodcommand.domain.entities;

import com.example.foodcommand.application.DTOs.*;
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

    // usuario Admin do Sistema - quem criou o foodcommand
    public Usuario(CriarUsuarioAdminSistemaRequest criarUsuarioAdminSistemaRequest) {
        this.setCpf(new CPF(criarUsuarioAdminSistemaRequest.cpf()));
        this.setNome(criarUsuarioAdminSistemaRequest.nome());
        this.setSenha(criarUsuarioAdminSistemaRequest.senha());
        this.setEmail(criarUsuarioAdminSistemaRequest.email());
        this.setRole("ROLE_ADMIN");
    }

    // usuario dono da empresa (Empresa e Dono)
    public Usuario(CriarEmpresaDonoRequest criarEmpresaDonoRequest) {
        this.setCpf(new CPF(criarEmpresaDonoRequest.cpf()));
        this.setNome(criarEmpresaDonoRequest.nome());
        this.setSenha(criarEmpresaDonoRequest.senha());
        this.setEmail(criarEmpresaDonoRequest.email());
        this.setRole("ROLE_EMPRESA_DONO");

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