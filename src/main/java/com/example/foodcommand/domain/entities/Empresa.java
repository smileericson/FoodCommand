package com.example.foodcommand.domain.entities;

import com.example.foodcommand.application.DTOs.CriarEmpresaDonoRequest;
import com.example.foodcommand.domain.valueobject.CNPJ;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;

    @Embedded
    private CNPJ cnpj;

    private String nomeFantasia;

    @OneToMany(mappedBy = "empresa")
    private List<Usuario> usuarios;

    public Empresa(CriarEmpresaDonoRequest criarEmpresaDonoRequest) {
            this.setRazaoSocial(criarEmpresaDonoRequest.razaoSocial());
            this.setCnpj(new CNPJ(criarEmpresaDonoRequest.cnpj()));
            this.setNomeFantasia(criarEmpresaDonoRequest.nomeFantasia());
    }
}
