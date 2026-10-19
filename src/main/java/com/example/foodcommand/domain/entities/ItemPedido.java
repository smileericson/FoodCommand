package com.example.foodcommand.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // pedido ao qual o item pertence - muitos itens para um pedido
    // Pedido tem a lista de itens e o item aponta de volta para o Pedido (relação bidirecional).
    // O @Data gera toString, equals e hashCode usando todos os campos, o que causaria loop infinito
    // (Pedido -> itens -> ItemPedido -> pedido -> Pedido...) e StackOverflowError.
    // @ToString.Exclude: não usa o campo pedido no toString(), quebrando o ciclo.
    // @EqualsAndHashCode.Exclude: não usa o campo pedido para comparar objetos (equals) nem no hashCode.
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pedido pedido;

    // prato do cardápio que foi pedido - muitos itens podem ser do mesmo prato
    @ManyToOne
    @JoinColumn(name = "cardapio_id")
    private Cardapio cardapio;

    // quantas unidades do prato
    private int quantidade;

    // preço copiado do cardápio no momento do pedido (não muda se o cardápio mudar depois)
    private BigDecimal precoUnitario;

    // cria o item já copiando o preço atual do cardápio
    public ItemPedido(Pedido pedido, Cardapio cardapio, int quantidade) {
        this.pedido = pedido;
        this.cardapio = cardapio;
        this.quantidade = quantidade;
        this.precoUnitario = cardapio.getPreco();
    }

    // valor total do item: preço x quantidade
    //BigDecimal.valueOf(quantidade) transforma o int em BigDecimal, porque só dá para multiplicar BigDecimal com BigDecimal.
    public BigDecimal getValorTotalItem() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}