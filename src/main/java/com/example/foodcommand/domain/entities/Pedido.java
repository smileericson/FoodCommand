package com.example.foodcommand.domain.entities;

import com.example.foodcommand.application.DTOs.EnumStatusMesa;
import com.example.foodcommand.application.DTOs.EnumStatusPedido;
import com.example.foodcommand.application.DTOs.PedidoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    // taxa de serviço de 10% - constante: static (uma só para a classe) e final (não muda)
    // esta com aspas pois sem herdaria o erro de precisão do double
    private static final BigDecimal TAXA_SERVICO = new BigDecimal("0.10");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public BigDecimal valorSubtotal;
    public BigDecimal taxaServico;
    public BigDecimal valorTotal;
    public EnumStatusPedido status = EnumStatusPedido.ATIVO;

    // PASSO 1 - mesa que está sendo atendida neste pedido
    // @ManyToOne: muitos pedidos podem ser da mesma mesa
    // @JoinColumn: cria a coluna mesa_id na tabela pedido, guardando só o id da mesa
    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    // PASSO 1 - garçom que abriu o atendimento (é um Usuario no papel de garçom)
    // @ManyToOne: muitos pedidos podem ser do mesmo garçom
    // @JoinColumn: cria a coluna garcom_id na tabela pedido, guardando só o id do usuário
    @ManyToOne
    @JoinColumn(name = "garcom_id")
    private Usuario garcom;

    // PASSO 5.2 - itens do pedido - um pedido tem muitos itens
    // mappedBy = "pedido": quem guarda a ligação no banco é o campo pedido do ItemPedido (coluna pedido_id)
    // cascade = ALL: ao salvar o pedido, os itens novos da lista são salvos juntos
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    // construtor usado na criação - o garçom NÃO vem do request por segurança,
    // ele será preenchido no service com o usuário logado (passo 3)
    public Pedido(PedidoRequest pedido) {
        this.setValorTotal(pedido.valorTotal());
        this.setTaxaServico(pedido.taxaServico());
        this.setValorSubtotal(pedido.valorSubtotal());
    }

    // PASSO 2 - regra de negócio: só o garçom que abriu o atendimento pode alterar o pedido
    // se não tiver garçom ou for outro garçom, lança exceção e interrompe a operação
    public void validarGarcom(Usuario garcomLogado) {
        if (this.garcom == null) {
            throw new RuntimeException("Este pedido não possui garçom responsável.");
        }
        if (!this.garcom.getId().equals(garcomLogado.getId())) {
            throw new RuntimeException("Somente o garçom que abriu o atendimento pode alterar esta mesa.");
        }
    }
    // PASSO 5.2 - só permite alterar pedido que ainda está ativo (não fechado, cancelado ou excluído)
    // esta como privado, pois é usado somente dentro do pedido, o service não pode chamar o recalcularValores sozinho, isto é
    // encapsulamento a entidade controla a própria consistencia
    private void validarAtivo() {
        if (this.status != EnumStatusPedido.ATIVO) {
            throw new RuntimeException("O pedido não está ativo.");
        }
    }
    // PASSO 5.2.1 - adiciona um prato ao pedido e recalcula os valores
    public void adicionarItem(Cardapio cardapio, int quantidade) {
        validarAtivo();
        if (quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }
        this.itens.add(new ItemPedido(this, cardapio, quantidade));
        recalcularValores();
    }
    // PASSO 5.2.2 - soma os itens, calcula 10% de taxa e o total
    //setScale deixa o valor em 2 casas decimais
    //half up é o arredondamento
    // esta como privado, pois é usado somente dentro do pedido, o service não pode chamar o recalcularValores sozinho, isto é
    // encapsulamento a entidade controla a própria consistencia
    //BigDecilam.ZERO = pedido recem aberto tem total 0 não null, sem isto a primeira soma daria NullPointerException
    private void recalcularValores() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            subtotal = subtotal.add(item.getValorTotalItem());
        }
        this.valorSubtotal = subtotal;
        this.taxaServico = subtotal.multiply(TAXA_SERVICO).setScale(2, RoundingMode.HALF_UP);
        this.valorTotal = this.valorSubtotal.add(this.taxaServico);
    }
    // PASSO 5.2.3 - garçom pede a conta: mesa fica aguardando fechamento
    // para pedir a conta tem que ter itens
    public void pedirConta() {
        validarAtivo();
        if (this.itens.isEmpty()) {
            throw new RuntimeException("Não é possível pedir a conta sem itens.");
        }
        this.mesa.setStatusMesa(EnumStatusMesa.AGUARDANDO_FECHAMENTO);
    }
    // PASSO 5.2.4 - fecha a conta: pedido fechado e mesa liberada para o próximo cliente
    // só fecha depois de pedir a conta.
    public void fecharConta() {
        validarAtivo();
        if (this.mesa.getStatusMesa() != EnumStatusMesa.AGUARDANDO_FECHAMENTO) {
            throw new RuntimeException("É preciso pedir a conta antes de fechar.");
        }
        this.status = EnumStatusPedido.FECHADO;
        this.mesa.setStatusMesa(EnumStatusMesa.LIVRE);
    }
}
