package com.example.foodcommand.presentation;

import com.example.foodcommand.application.DTOs.AtualizarStatusPedido;
import com.example.foodcommand.domain.entities.EnumStatusPedido;
import com.example.foodcommand.domain.entities.Pedido;
import com.example.foodcommand.domain.repository.PedidoRepository;
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

    // GET /pedidos -> lista todos.
    //(Operation)summary: descreve a documentação da api, description:descreve a regra de negocio.
    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de pedidos",description = "Metodo responsavel em efetuar consulta de todos os pedidos sem filtro")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    // GET /pedidos/{id} -> busca um pelo id.
    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de Pedido",description= "Metodo responsavel em efetuar a consulta de todos os pedidos por Id")
    public ResponseEntity<Pedido>buscarPorId(@PathVariable Long id){
        Pedido pedidoBanco = pedidoRepository.findById(id).orElse(null);
        if(pedidoBanco != null ){
            return  ResponseEntity.ok(pedidoBanco);
        }
        return ResponseEntity.notFound().build();

    }

    // POST /pedidos -> cadastra um novo.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de consulta de pedidos", description = "Metodo responsavel em criar novos pedidos")
    public ResponseEntity<Pedido>criar(@RequestBody Pedido pedido){
        var pedidoBanco = pedidoRepository.save(pedido);
        return ResponseEntity.ok(pedidoBanco);
    }

    // PATCH /pedidos/{id}/status -> troca so o status.
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

    // PUT /pedidos/{id} -> atualiza nome, setor, email e status.
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

    // DELETE /pedidos/{id}/excluir -> exclusao logica: so troca o status pra EXCLUIDO.
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
