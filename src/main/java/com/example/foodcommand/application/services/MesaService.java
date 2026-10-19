package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.MesaRequest;
import com.example.foodcommand.application.DTOs.MesaResponse;
import com.example.foodcommand.application.DTOs.EnumStatusMesa;
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
    public MesaResponse buscarPorId(Long id) {
        return mesaRepository.findById(id)
                .map(MesaResponse::new)
                .orElse(null);
    }

    public MesaResponse salvarMesa(MesaRequest mesa, Long id) {
        if (id == null || id == 0L) {
            Mesa mesaBanco = mesaRepository.save(new Mesa(mesa));
            return new MesaResponse(mesaBanco);
        }

        return mesaRepository.findById(id)
                .map(mesaBanco -> {
                    mesaBanco.setNumeroMesa(mesa.numeroMesa());
                    return new MesaResponse(mesaRepository.save(mesaBanco));
                })
                .orElse(null);
    }

    public boolean atualizarStatus(EnumStatusMesa status, Long id) {
        return mesaRepository.findById(id)
                .map(mesa -> {
                    mesa.setStatusMesa(status);
                    mesaRepository.save(mesa);
                    return true;
                })
                .orElse(false);
    }
}
