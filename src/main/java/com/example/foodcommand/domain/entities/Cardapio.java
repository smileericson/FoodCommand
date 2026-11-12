package com.example.foodcommand.domain.entities;


import com.example.foodcommand.application.DTOs.CardapioRequest;
import com.example.foodcommand.application.DTOs.EnumStatusCardapio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    public Cardapio(CardapioRequest cardapio) {
        this.setNome(cardapio.nome());
        this.setDescricao(cardapio.descricao());
        this.setPreco(cardapio.preco());
    }
}
