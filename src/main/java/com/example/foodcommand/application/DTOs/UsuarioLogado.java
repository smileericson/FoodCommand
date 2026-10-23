package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.Usuario;
import com.example.foodcommand.domain.valueobject.CPF;

public record UsuarioLogado(Long id, String email, CPF Cpf,
                            java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {

    public UsuarioLogado(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getAuthorities()
        );

    }
}
