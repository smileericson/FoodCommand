package com.example.foodcommand.controllers;

import com.example.foodcommand.entities.Pedido;
import com.example.foodcommand.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
//tag
@Tag(name="Pedidos",description = "Grupo de APIs resposanavel por controlar a estrutura e cosunta de pedidos do sistema!")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    //operation
    @Operation(summary = "Metodo de consulta de lista de pedidos",description = "Metodo responsavel em efetuar consulta de todos os pedidos sem filtro")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //operation
    @Operation(summary = "Metodo de consulta de pedidos", description = "Metodo responsavel em criar novos pedidos")
    public ResponseEntity<Pedido>criar(@RequestBody Pedido pedido){
        var pedidoBanco = pedidoRepository.save(pedido);
        return ResponseEntity.ok(pedidoBanco);
    }
}
