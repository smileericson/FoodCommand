package com.example.foodcommand.domain.repository;

import com.example.foodcommand.domain.entities.TipoRestaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TipoRestauranteRepository extends JpaRepository<TipoRestaurante,Long> {


}
