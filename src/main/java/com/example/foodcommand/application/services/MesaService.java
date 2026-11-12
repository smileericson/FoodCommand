package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.MesaRequest;
import com.example.foodcommand.application.DTOs.MesaResponse;
import com.example.foodcommand.application.DTOs.EnumStatusMesa;
import com.example.foodcommand.domain.entities.Mesa;
import com.example.foodcommand.domain.repository.EmpresaRepository;
import com.example.foodcommand.domain.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmpresaRepository empresaRepository;

    private Long empresaLogadaId(){
        return usuarioService.getUsuarioLogado().empresa_id();
    }

    public List<MesaResponse> listarTodasMesas() {
        var mesas = empresaLogadaId() == null
                ? mesaRepository.findAll()
                : mesaRepository.findAllByEmpresa_Id(empresaLogadaId());
        return mesas
                .stream()
                .map(MesaResponse:: new)
                .toList();
    }
    public MesaResponse buscarPorId(Long id) {
        var mesa = empresaLogadaId() == null
                ? mesaRepository.findById(id)
                : mesaRepository.findByIdAndEmpresa_Id(id, empresaLogadaId());
        return mesa
                .map(MesaResponse::new)
                .orElse(null);
    }

    public MesaResponse salvarMesa(MesaRequest mesa, Long id) {
        if (id == null || id == 0L) {
            Mesa novaMesa= new Mesa(mesa);

            novaMesa.setEmpresa(empresaRepository.findById(empresaLogadaId())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada")));

            Mesa mesaBanco = mesaRepository.save(novaMesa);

            return new MesaResponse(mesaBanco);
        }

        return mesaRepository.findByIdAndEmpresa_Id(id,empresaLogadaId())
                .map(mesaBanco -> {
                    mesaBanco.setNumeroMesa(mesa.numeroMesa());
                    return new MesaResponse(mesaRepository.save(mesaBanco));
                })
                .orElse(null);
    }

    public boolean atualizarStatus(EnumStatusMesa status, Long id) {
        return mesaRepository.findByIdAndEmpresa_Id(id, empresaLogadaId())
                .map(mesa -> {
                    mesa.setStatusMesa(status);
                    mesaRepository.save(mesa);
                    return true;
                })
                .orElse(false);
    }
}
