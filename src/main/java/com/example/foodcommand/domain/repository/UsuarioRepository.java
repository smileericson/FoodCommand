package com.example.foodcommand.domain.repository;

import com.example.foodcommand.application.DTOs.EnumStatusUsuario;
import com.example.foodcommand.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// Indica que esta interface é um repositório de persistência.
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    //O repositório trabalha com a entidade Usuario e utiliza Long como tipo do identificador.

    boolean existsUsuarioByEmailAndSenha(String email, String senha);
    // Verifica se existe um usuário com o e-mail e a senha informados.
    // Retorna true se encontrar um registro correspondente
    // e false caso contrário.

    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario status);
    // Busca usuários cujo status seja diferente do status informado.
    // Retorna um Optional contendo uma lista de usuários,
    // ou um Optional vazio caso não exista resultado.
}
