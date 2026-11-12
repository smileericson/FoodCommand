package com.example.foodcommand.domain.repository;

import com.example.foodcommand.domain.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface MesaRepository extends JpaRepository<Mesa,Long> {

    List<Mesa> findAllByEmpresa_Id(Long empresaId);                 // NOVO

    Optional<Mesa> findByIdAndEmpresa_Id(Long id, Long empresaId);
}
