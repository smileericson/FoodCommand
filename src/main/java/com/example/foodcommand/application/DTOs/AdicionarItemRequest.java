package com.example.foodcommand.application.DTOs;

public record AdicionarItemRequest(Long cardapioId,int quantidade) {
}

//Por que um DTO novo: o PedidoRequest tem os valores do pedido,
// mas para adicionar um item o front só precisa mandar qual prato e quantos. O garçom vem do token e o
// pedido vem da URL. Cada DTO deve levar só o que a ação precisa.