package com.example.foodcommand.domain.repository;

import com.example.foodcommand.domain.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
