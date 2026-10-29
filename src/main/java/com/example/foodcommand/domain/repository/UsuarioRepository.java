package com.example.foodcommand.domain.repository;

import com.example.foodcommand.application.DTOs.EnumStatusUsuario;
import com.example.foodcommand.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    boolean existsUsuarioByEmailAndSenha(String email, String senha);

    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario status);

    List<Usuario> getUsuariosByEmpresa_Id(Long empesa);

}
