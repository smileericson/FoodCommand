package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.PedidoResponse;
import com.example.foodcommand.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PedidoResponse> listarTodosPedidosTable(){
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponse::new)
                .toList();
    }
}
