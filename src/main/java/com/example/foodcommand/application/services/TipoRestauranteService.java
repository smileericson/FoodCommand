package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.CriarTipoRestauranteRequest;
import com.example.foodcommand.application.DTOs.CriarTipoRestauranteResponse;
import com.example.foodcommand.application.DTOs.TipoRestauranteResponse;
import com.example.foodcommand.domain.entities.TipoRestaurante;
import com.example.foodcommand.domain.repository.TipoRestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TipoRestauranteService {

    @Value("${spring.secret}")
    private String secret;

    @Autowired
    private TipoRestauranteRepository tipoRestauranteRepository;

    public List<TipoRestauranteResponse> listarTodosTiposRestauranteTable(){
        return tipoRestauranteRepository.findAll()
                .stream()
                .map(TipoRestauranteResponse::new)
                .toList();
    }

    public CriarTipoRestauranteResponse criarTipoRestaurante(CriarTipoRestauranteRequest tipoRestauranteRequest){
        if(!tipoRestauranteRequest.secretKey().equals(secret)){
            throw new RuntimeException("Chave secreta inválida");
        }
        TipoRestaurante tipoRestauranteSalvar = new TipoRestaurante(tipoRestauranteRequest);

        tipoRestauranteRepository.save(tipoRestauranteSalvar);

        return new CriarTipoRestauranteResponse(tipoRestauranteSalvar.getId(),"Tipo de Restaurante salvo com sucesso");
    }
}
