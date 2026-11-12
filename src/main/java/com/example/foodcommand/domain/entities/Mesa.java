package com.example.foodcommand.domain.entities;

import com.example.foodcommand.application.DTOs.EnumStatusMesa;
import com.example.foodcommand.application.DTOs.MesaRequest;
import jakarta.persistence.*;
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
    public int numeroMesa;
    public EnumStatusMesa statusMesa = EnumStatusMesa.LIVRE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    public Mesa(MesaRequest mesa) {
        this.setNumeroMesa(mesa.numeroMesa());
    }
}
