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
public class Mesa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public int numero;
    public Status status;

    public enum Status {
        LIVRE,
        OCUPADA,
        FECHADA
    }
}
