package com.example.foodcommand.repository;

import com.example.foodcommand.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository  extends JpaRepository<Pedido, Long> {
}
