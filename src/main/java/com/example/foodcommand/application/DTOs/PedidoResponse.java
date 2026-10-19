package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.Pedido;

import java.math.BigDecimal;

public record PedidoResponse(long id, BigDecimal valorSubtotal, BigDecimal taxaServico, BigDecimal valorTotal, EnumStatusPedido statusPedido) {

    public PedidoResponse(Pedido pedidoEntidade){
        this(
                pedidoEntidade.getId(),
                pedidoEntidade.getValorSubtotal(),
                pedidoEntidade.getTaxaServico(),
                pedidoEntidade.getValorTotal(),
                pedidoEntidade.getStatus()
        );
    }
}
