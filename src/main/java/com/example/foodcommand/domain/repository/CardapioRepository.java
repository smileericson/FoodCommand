package com.example.foodcommand.domain.repository;

import com.example.foodcommand.domain.entities.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
}
