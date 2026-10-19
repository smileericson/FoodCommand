package com.example.foodcommand.domain.entities;


import com.example.foodcommand.application.DTOs.CardapioRequest;
import com.example.foodcommand.application.DTOs.EnumStatusCardapio;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String descricao;
    public BigDecimal preco;
    public EnumStatusCardapio status = EnumStatusCardapio.ATIVO;

    public Cardapio(CardapioRequest cardapio) {
        this.setNome(cardapio.nome());
        this.setDescricao(cardapio.descricao());
        this.setPreco(cardapio.preco());
    }
}
