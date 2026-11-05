package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.DTOs.EnumStatusPedido;
import com.example.foodcommand.domain.entities.Cardapio;
import com.example.foodcommand.domain.entities.Mesa;
import com.example.foodcommand.domain.entities.Pedido;
import com.example.foodcommand.domain.entities.Usuario;
import com.example.foodcommand.domain.repository.CardapioRepository;
import com.example.foodcommand.domain.repository.MesaRepository;
import com.example.foodcommand.domain.repository.PedidoRepository;
import com.example.foodcommand.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PedidoResponse> listarTodosPedidosTable(){
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponse::new)
                .toList();
    }

    public PedidoResponse salvarPedido(PedidoRequest pedido, Long id) {
        if (id == null || id == 0L) {
            Pedido pedidoBanco = pedidoRepository.save(new Pedido(pedido));
            return new PedidoResponse(pedidoBanco);
        }
        return pedidoRepository.findById(id)
                .map(pedidoBanco -> {
                    pedidoBanco.setValorSubtotal(pedido.valorSubtotal());
                    pedidoBanco.setTaxaServico(pedido.taxaServico());
                    pedidoBanco.setValorTotal(pedido.valorTotal());
                    return new PedidoResponse(pedidoRepository.save(pedidoBanco));
                })
                .orElse(null);
    }

    public PedidoResponse buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoResponse::new)
                .orElse(null);
    }

    public boolean atualizarStatus(EnumStatusPedido status, Long id){
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(status);
                    pedidoRepository.save(pedido);
                    return true;
                })
                .orElse(false);
    }

    private Usuario buscarGarcomLogado() {
        var usuarioLogado = usuarioService.getUsuarioLogado();
        return usuarioRepository.findById(usuarioLogado.id())
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado."));
    }

    private Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
    }

    @Transactional
    public PedidoResponse abrirAtendimento(Long mesaId) {

        Usuario garcomLogado = buscarGarcomLogado();

        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada."));

        if (mesa.getStatusMesa() != EnumStatusMesa.LIVRE) {
            throw new RuntimeException("A mesa não está livre para abrir atendimento.");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setGarcom(garcomLogado);
        pedido.setStatus(EnumStatusPedido.ATIVO);

        mesa.setStatusMesa(EnumStatusMesa.OCUPADA);
        mesaRepository.save(mesa);

        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse adicionarItem(Long pedidoId, Long cardapioId, int quantidade) {

        Usuario garcomLogado = buscarGarcomLogado();

        Pedido pedido = buscarPedido(pedidoId);
        pedido.validarGarcom(garcomLogado);

        Cardapio cardapio = cardapioRepository.findById(cardapioId)
                .orElseThrow(() -> new RuntimeException("Item do cardápio não encontrado."));
        if (cardapio.getStatus() != EnumStatusCardapio.ATIVO) {
            throw new RuntimeException("Este item não está disponível no cardápio.");
        }

        pedido.adicionarItem(cardapio, quantidade);

        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse enviarParaPreparo(Long pedidoId) {

        Usuario garcomLogado = buscarGarcomLogado();

        Pedido pedido = buscarPedido(pedidoId);
        pedido.validarGarcom(garcomLogado);

        pedido.enviarParaPreparo();

        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse marcarPronto(Long pedidoId) {

        Pedido pedido = buscarPedido(pedidoId);

        pedido.marcarPronto();

        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse pedirConta(Long pedidoId) {

        Usuario garcomLogado = buscarGarcomLogado();

        Pedido pedido = buscarPedido(pedidoId);
        pedido.validarGarcom(garcomLogado);

        pedido.pedirConta();

        mesaRepository.save(pedido.getMesa());
        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse fecharConta(Long pedidoId) {

        Usuario garcomLogado = buscarGarcomLogado();

        Pedido pedido = buscarPedido(pedidoId);
        pedido.validarGarcom(garcomLogado);

        pedido.fecharConta();

        mesaRepository.save(pedido.getMesa());
        return new PedidoResponse(pedidoRepository.save(pedido));
    }
}