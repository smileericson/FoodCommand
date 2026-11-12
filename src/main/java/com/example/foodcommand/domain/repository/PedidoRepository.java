package com.example.foodcommand.domain.repository;

import com.example.foodcommand.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface PedidoRepository  extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByEmpresa_Id(Long empresaId);                   // NOVO

    Optional<Pedido> findByIdAndEmpresa_Id(Long id, Long empresaId);

}
