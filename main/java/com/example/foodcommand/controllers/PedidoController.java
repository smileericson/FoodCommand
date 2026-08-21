package com.example.foodcommand.controllers;

import com.example.foodcommand.entities.Pedido;
import com.example.foodcommand.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pedido>criar(@RequestBody Pedido pedido){
        var pedidoBanco = pedidoRepository.save(pedido);
        return ResponseEntity.ok(pedidoBanco);
    }
}
