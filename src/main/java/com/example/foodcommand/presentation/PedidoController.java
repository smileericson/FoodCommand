package com.example.foodcommand.presentation;


import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.services.PedidoService;
import com.example.foodcommand.application.DTOs.EnumStatusPedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name="Pedidos",description = "Grupo de APIs resposanavel por controlar a estrutura e consulta de Pedidos do sistema!")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarTodos(){
        return ResponseEntity.ok(pedidoService.listarTodosPedidosTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a consulta de todos os pedidos por Id")
    public ResponseEntity<PedidoResponse>buscarPorId(@PathVariable Long id){
        PedidoResponse pedidoBanco = pedidoService.buscarPorId(id);
        if(pedidoBanco != null ){
            return  ResponseEntity.ok(pedidoBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description = "Metodo responsavel em efetuar a criação de novos pedidos")
    public ResponseEntity<?> criar(@RequestBody PedidoRequest pedido){
        try {
            PedidoResponse pedidoBanco =  pedidoService.salvarPedido(pedido,0L);
            return ResponseEntity.ok(pedidoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/mesa/{mesaId}/abrir")
    @Operation(summary = "Abrir atendimento", description = "O garçom logado abre o atendimento de uma mesa livre")
    public ResponseEntity<?> abrirAtendimento(@PathVariable Long mesaId) {
        try {
            return ResponseEntity.ok(pedidoService.abrirAtendimento(mesaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/itens")
    @Operation(summary = "Adicionar item", description = "Adiciona um item ao pedido. Somente o garçom que abriu o atendimento")
    public ResponseEntity<?> adicionarItem(@PathVariable Long id,
                                           @RequestBody AdicionarItemRequest itemRequest) {
        try {
            return ResponseEntity.ok(pedidoService.adicionarItem(id, itemRequest.cardapioId(), itemRequest.quantidade()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("/{id}/enviar-preparo")
    @Operation(summary = "Enviar para o preparo", description = "Envia o pedido para a cozinha. Somente o garçom que abriu o atendimento")
    public ResponseEntity<?> enviarParaPreparo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.enviarParaPreparo(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/pronto")
    @Operation(summary = "Marcar como pronto", description = "A cozinha informa que o pedido terminou o preparo")
    public ResponseEntity<?> marcarPronto(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.marcarPronto(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/pedir-conta")
    @Operation(summary = "Pedir a conta", description = "Coloca a mesa aguardando fechamento. Somente o garçom que abriu o atendimento")
    public ResponseEntity<?> pedirConta(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.pedirConta(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/fechar-conta")
    @Operation(summary = "Fechar a conta", description = "Fecha o pedido e libera a mesa. Somente o garçom que abriu o atendimento")
    public ResponseEntity<?> fecharConta(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.fecharConta(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping ("/{id}/status")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização de todos os pedidos por Id")
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id,
                                               @RequestBody AtualizarStatusPedido statusRequest){
        boolean atualizarPedido = pedidoService.atualizarStatus(statusRequest.statusPedido(),id);
        if(atualizarPedido){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização de todos os pedidos,filtrado por Id")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody PedidoRequest pedido) {
        try {
            PedidoResponse pedidoBanco = pedidoService.salvarPedido(pedido,id);
            if (pedidoBanco == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pedidoBanco);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarPedido = pedidoService.atualizarStatus(EnumStatusPedido.EXCLUIDO,id);
        if(atualizarPedido){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}