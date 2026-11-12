package com.example.foodcommand.domain.repository;

import com.example.foodcommand.domain.entities.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    List<Cardapio> findAllByEmpresa_Id(Long empresaId);                 // NOVO - lista só da empresa

    Optional<Cardapio> findByIdAndEmpresa_Id(Long id, Long empresaId);  // NOVO - busca por id SÓ se for da empresa
}
