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

    private static final BigDecimal TAXA_SERVICO = new BigDecimal("0.10");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public BigDecimal valorSubtotal;
    public BigDecimal taxaServico;
    public BigDecimal valorTotal;
    public EnumStatusPedido status = EnumStatusPedido.ATIVO;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "garcom_id")
    private Usuario garcom;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido(PedidoRequest pedido) {
        this.setValorTotal(pedido.valorTotal());
        this.setTaxaServico(pedido.taxaServico());
        this.setValorSubtotal(pedido.valorSubtotal());
    }

    public void validarGarcom(Usuario garcomLogado) {
        if (this.garcom == null) {
            throw new RuntimeException("Este pedido não possui garçom responsável.");
        }
        if (!this.garcom.getId().equals(garcomLogado.getId())) {
            throw new RuntimeException("Somente o garçom que abriu o atendimento pode alterar esta mesa.");
        }
    }

    private void validarEmAtendimento() {
        if (this.status != EnumStatusPedido.ATIVO && this.status != EnumStatusPedido.EM_PREPARO && this.status != EnumStatusPedido.PRONTO) {
            throw new RuntimeException("O pedido nao esta em atendimento.");
        }
    }

    public void adicionarItem(Cardapio cardapio, int quantidade) {
        validarEmAtendimento();
        if (quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }
        this.itens.add(new ItemPedido(this, cardapio, quantidade));
        this.status = EnumStatusPedido.ATIVO;
        recalcularValores();
    }

    private void recalcularValores() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            subtotal = subtotal.add(item.getValorTotalItem());
        }
        this.valorSubtotal = subtotal;
        this.taxaServico = subtotal.multiply(TAXA_SERVICO).setScale(2, RoundingMode.HALF_UP);
        this.valorTotal = this.valorSubtotal.add(this.taxaServico);
    }

    public void enviarParaPreparo() {
        if (this.status != EnumStatusPedido.ATIVO) {
            throw new RuntimeException("So e possivel enviar para o preparo um pedido ativo.");
        }
        if (this.itens.isEmpty()) {
            throw new RuntimeException("Nao e possivel enviar para o preparo sem itens.");
        }
        this.status = EnumStatusPedido.EM_PREPARO;
    }

    public void marcarPronto() {
        if (this.status != EnumStatusPedido.EM_PREPARO) {
            throw new RuntimeException("So um pedido em preparo pode ser marcado como pronto.");
        }
        this.status = EnumStatusPedido.PRONTO;
    }

    public void pedirConta() {
        validarEmAtendimento();
        if (this.itens.isEmpty()) {
            throw new RuntimeException("Não é possível pedir a conta sem itens.");
        }
        this.mesa.setStatusMesa(EnumStatusMesa.AGUARDANDO_FECHAMENTO);
    }

    public void fecharConta() {
        validarEmAtendimento();
        if (this.status != EnumStatusPedido.PRONTO) {
            throw new RuntimeException("O pedido precisa estar pronto antes de fechar a conta.");
        }
        if (this.mesa.getStatusMesa() != EnumStatusMesa.AGUARDANDO_FECHAMENTO) {
            throw new RuntimeException("É preciso pedir a conta antes de fechar.");
        }
        this.status = EnumStatusPedido.FECHADO;
        this.mesa.setStatusMesa(EnumStatusMesa.LIVRE);
    }
}