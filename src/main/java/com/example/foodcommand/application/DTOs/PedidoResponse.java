package com.example.foodcommand.application.DTOs;

import com.example.foodcommand.domain.entities.EnumStatusPedido;
import com.example.foodcommand.domain.entities.Pedido;

public record PedidoResponse(long id, int valorSubtotal, int taxaServico, int valorTotal, EnumStatusPedido statusPedido) {

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
