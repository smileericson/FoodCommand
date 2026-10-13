package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.CardapioResponse;
import com.example.foodcommand.domain.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class CardapioService {


    @Autowired
    private CardapioRepository cardapioRepository;

    public List<CardapioResponse> listarTodosCardapiosTable(){
        return cardapioRepository.findAll()
                .stream()
                .map(CardapioResponse::new)
                .toList();
    }
}
