package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.MesaResponse;
import com.example.foodcommand.domain.entities.Mesa;
import com.example.foodcommand.domain.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public List<MesaResponse> listarTodasMesas() {
        return mesaRepository.findAll()
                .stream()
                .map(MesaResponse:: new)
                .toList();
    }
}
