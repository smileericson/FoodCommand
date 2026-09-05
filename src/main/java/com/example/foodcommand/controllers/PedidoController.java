package com.example.foodcommand.controllers;

import com.example.foodcommand.DTOs.AtualizarStatusPedido;
import com.example.foodcommand.entities.EnumStatusPedido;
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
@Tag(name="Pedidos",description = "Grupo de APIs resposanavel por controlar a estrutura e consulta de pedidos do sistema!")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    //operation
    @Operation(summary = "Metodo de consulta de lista de pedidos",description = "Metodo responsavel em efetuar consulta de todos os pedidos sem filtro")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de Pedido",description= "Metodo responsavel em efetuar a consulta de todos os pedidos por Id")
    public ResponseEntity<Pedido>buscarPorId(@PathVariable Long id){
        Pedido pedidoBanco = pedidoRepository.findById(id).orElse(null);
        if(pedidoBanco != null ){
            return  ResponseEntity.ok(pedidoBanco);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //operation
    @Operation(summary = "Metodo de consulta de pedidos", description = "Metodo responsavel em criar novos pedidos")
    public ResponseEntity<Pedido>criar(@RequestBody Pedido pedido){
        var pedidoBanco = pedidoRepository.save(pedido);
        return ResponseEntity.ok(pedidoBanco);
    }

    @PatchMapping ("/{id}/status")// serve para atualizar um dado só
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização de todos os pedidos por Id")
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusPedido statusRequest){

        Pedido pedidoBanco = pedidoRepository.findById(id).orElse(null);
        if(pedidoBanco != null ){
            pedidoBanco.setStatusPedido(statusRequest.statusPedido());
            pedidoRepository.save((pedidoBanco));
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização de todos os pedidos,filtrado por Id")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id,@RequestBody Pedido pedido) {
        try {
            Pedido pedidoBanco = pedidoRepository.findById(id).orElse(null);
            if (pedidoBanco != null) {
                pedidoBanco.setStatusPedido(pedido.getStatusPedido());
                pedidoBanco.setValorSubtotal(pedido.getValorSubtotal());
                pedidoBanco.setTaxaServico(pedido.getTaxaServico());
                pedidoBanco.setValorTotal(pedido.getValorTotal());
                pedidoRepository.save((pedidoBanco));
                return ResponseEntity.ok().build();

            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Pedido pedidoBanco = pedidoRepository.findById(id).orElse(null);
        if(pedidoBanco != null ){
            pedidoBanco.setStatusPedido(EnumStatusPedido.EXCLUIDO);
            pedidoRepository.save((pedidoBanco));
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
