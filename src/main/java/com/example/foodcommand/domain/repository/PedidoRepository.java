package com.example.foodcommand.domain.repository;

import com.example.foodcommand.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository  extends JpaRepository<Pedido, Long> {
}
