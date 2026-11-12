package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.CardapioRequest;
import com.example.foodcommand.application.DTOs.CardapioResponse;
import com.example.foodcommand.domain.entities.Cardapio;
import com.example.foodcommand.application.DTOs.EnumStatusCardapio;
import com.example.foodcommand.domain.repository.CardapioRepository;
import com.example.foodcommand.domain.repository.EmpresaRepository;
import com.example.foodcommand.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class CardapioService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private CardapioRepository cardapioRepository;

    //pega o id da empresa do usuário logado (vem do token, pelo JwtFilter)
    private Long empresaLogadaId(){
        return usuarioService.getUsuarioLogado().empresa_id();
    }

    public List<CardapioResponse> listarTodosCardapiosTable(){
        var cardapios = empresaLogadaId() == null
                ? cardapioRepository.findAll()
                : cardapioRepository.findAllByEmpresa_Id(empresaLogadaId());
        return cardapios
                .stream()
                .map(CardapioResponse::new)
                .toList();
    }
    public CardapioResponse buscarPorId(Long id) {
        var cardapio = empresaLogadaId() == null
                ? cardapioRepository.findById(id)
                : cardapioRepository.findByIdAndEmpresa_Id(id,empresaLogadaId());
        return cardapio
                .map(CardapioResponse::new)
                .orElse(null);
    }

    public CardapioResponse salvarCardapio(CardapioRequest cardapio, Long id) {
        if (id == null || id == 0L) {
            Cardapio novoCardapio = new Cardapio(cardapio);

            novoCardapio.setEmpresa(empresaRepository.findById(empresaLogadaId())
                    .orElseThrow(()-> new RuntimeException("Empresa não encontrada")));
            Cardapio cardapioBanco = cardapioRepository.save(novoCardapio);

            return new CardapioResponse(cardapioBanco);

        }

        return cardapioRepository.findByIdAndEmpresa_Id(id,empresaLogadaId())
                .map(cardapioBanco -> {
                    cardapioBanco.setNome(cardapio.nome());
                    cardapioBanco.setDescricao(cardapio.descricao());
                    cardapioBanco.setPreco(cardapio.preco());
                    return new CardapioResponse(cardapioRepository.save(cardapioBanco));
                })
                .orElse(null);
    }

    public boolean atualizarStatus(EnumStatusCardapio status, Long id) {
        return cardapioRepository.findByIdAndEmpresa_Id(id,empresaLogadaId())
                .map(cardapio -> {
                    cardapio.setStatus(status);
                    cardapioRepository.save(cardapio);
                    return true;
                })
                .orElse(false);
    }
}
