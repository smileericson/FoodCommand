package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.CardapioRequest;
import com.example.foodcommand.application.DTOs.CardapioResponse;
import com.example.foodcommand.domain.entities.Cardapio;
import com.example.foodcommand.application.DTOs.EnumStatusCardapio;
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
    public CardapioResponse buscarPorId(Long id) {
        return cardapioRepository.findById(id)
                .map(CardapioResponse::new)
                .orElse(null);
    }

    public CardapioResponse salvarCardapio(CardapioRequest cardapio, Long id) {
        if (id == null || id == 0L) {
            Cardapio cardapioBanco = cardapioRepository.save(new Cardapio(cardapio));
            return new CardapioResponse(cardapioBanco);
        }

        return cardapioRepository.findById(id)
                .map(cardapioBanco -> {
                    cardapioBanco.setNome(cardapio.nome());
                    cardapioBanco.setDescricao(cardapio.descricao());
                    cardapioBanco.setPreco(cardapio.preco());
                    return new CardapioResponse(cardapioRepository.save(cardapioBanco));
                })
                .orElse(null);
    }

    public boolean atualizarStatus(EnumStatusCardapio status, Long id) {
        return cardapioRepository.findById(id)
                .map(cardapio -> {
                    cardapio.setStatus(status);
                    cardapioRepository.save(cardapio);
                    return true;
                })
                .orElse(false);
    }
}
