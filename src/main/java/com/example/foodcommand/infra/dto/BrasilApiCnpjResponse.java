package com.example.foodcommand.infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrasilApiCnpjResponse {

    @JsonProperty("razao_social")
    private String razaoSocial;

    @JsonProperty("nome_fantasia")
    private String nomeFantasia;

    private String cep;
    private String logradouro;
    private String bairro;

    @JsonProperty("municipio")
    private String cidade;

    private String uf;

}
