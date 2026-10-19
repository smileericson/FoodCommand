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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // PASSO 3 - usado para buscar a mesa e marcar como ocupada ao abrir o atendimento
    @Autowired
    private MesaRepository mesaRepository;

    // usado para buscar o prato do cardápio ao adicionar item
    @Autowired
    private CardapioRepository cardapioRepository;

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

    // PASSO 3 - abre o atendimento de uma mesa, gravando o garçom que abriu
    // @Transactional: salva a mesa e o pedido juntos; se um falhar, nenhum é salvo
    @Transactional
    public PedidoResponse abrirAtendimento(Long mesaId, Usuario garcomLogado) {

        // 1 - busca a mesa; se não existir, lança exceção
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada."));

        // 2 - só abre atendimento se a mesa estiver livre (impede dois garçons na mesma mesa)
        if (mesa.getStatusMesa() != EnumStatusMesa.LIVRE) {
            throw new RuntimeException("A mesa não está livre para abrir atendimento.");
        }

        // 3 - cria o pedido já com a mesa e o garçom responsável
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setGarcom(garcomLogado);
        pedido.setStatus(EnumStatusPedido.ATIVO);

        // 4 - a mesa passa a ficar ocupada
        mesa.setStatusMesa(EnumStatusMesa.OCUPADA);
        mesaRepository.save(mesa);

        return new PedidoResponse(pedidoRepository.save(pedido));
    }
    // PASSO 5.3 - busca o pedido pelo id; se não existir, lança exceção
    // método auxiliar usado pelos três métodos abaixo, para não repetir o mesmo código evitando duplicidade
    //É private por que só a propria service usa
    private Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
    }

    // PASSO 5.3 - adiciona um prato ao pedido (só o garçom que abriu o atendimento)
    @Transactional
    public PedidoResponse adicionarItem(Long pedidoId, Long cardapioId, int quantidade, Usuario garcomLogado) {

        // 1 - busca o pedido e valida se o garçom logado é o dono do atendimento
        Pedido pedido = buscarPedido(pedidoId);
        pedido.validarGarcom(garcomLogado);

        // 2 - busca o prato e só permite adicionar se estiver ativo no cardápio
        // A service busca o Cardapio e não a entidade , por que a entidade pedido não acessa o banco , quem busca é a service
        // que depois entra o cardapio pronto para o pedido.adicionarItem
        // a divisão fica : o service busca e salva, a entidade decide as regras de negocios.
        // Validar cardapio ativo evita de adicionar itens desativados ou excluidos.
        Cardapio cardapio = cardapioRepository.findById(cardapioId)
                .orElseThrow(() -> new RuntimeException("Item do cardápio não encontrado."));
        if (cardapio.getStatus() != EnumStatusCardapio.ATIVO) {
            throw new RuntimeException("Este item não está disponível no cardápio.");
        }

        // 3 - regra da entidade: adiciona o item e recalcula os valores
        pedido.adicionarItem(cardapio, quantidade);

        // 4 - salva o pedido;
        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    // PASSO 5.3 - garçom pede a conta: mesa vai para AGUARDANDO_FECHAMENTO
    @Transactional
    public PedidoResponse pedirConta(Long pedidoId, Usuario garcomLogado) {
        Pedido pedido = buscarPedido(pedidoId);
        pedido.validarGarcom(garcomLogado);

        pedido.pedirConta();

        mesaRepository.save(pedido.getMesa());
        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    // PASSO 5.3 - fecha a conta: pedido FECHADO e mesa LIVRE
    @Transactional
    public PedidoResponse fecharConta(Long pedidoId, Usuario garcomLogado) {
        Pedido pedido = buscarPedido(pedidoId);
        pedido.validarGarcom(garcomLogado);

        pedido.fecharConta();

        mesaRepository.save(pedido.getMesa());
        return new PedidoResponse(pedidoRepository.save(pedido));
    }
}