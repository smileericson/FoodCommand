package com.example.foodcommand.application.DTOs;

import java.math.BigDecimal;

public record PedidoRequest(BigDecimal valorSubtotal, BigDecimal taxaServico, BigDecimal valorTotal) {
}
