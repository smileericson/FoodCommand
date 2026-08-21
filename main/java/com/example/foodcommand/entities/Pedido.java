package com.example.foodcommand.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public int valorSubtotal;
    public int taxaServico;
    public int valorTotal;
    public Status status;

    public enum Status{
        ATIVO,
        CANCELADO
    }

}
