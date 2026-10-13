package com.example.foodcommand.domain.entities;

import com.example.foodcommand.application.DTOs.CriarTipoRestauranteRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TipoRestaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String nome;

    public TipoRestaurante(CriarTipoRestauranteRequest tipoRestauranteRequest){
        this.setNome(tipoRestauranteRequest.nome());
    }
}
