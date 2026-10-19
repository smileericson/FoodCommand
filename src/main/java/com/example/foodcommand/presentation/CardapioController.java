package com.example.foodcommand.presentation;

import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.services.CardapioService;
import com.example.foodcommand.application.DTOs.EnumStatusCardapio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
@Tag(name="Cardapio",description = "Grupo de APIs responsavel por controlar a estrutura e consultar o Cardapio do sistema")
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;

    // get /cardapio - lista todos os cardapios do banco.
    //(Operation)summary: descreve a documentação da api, description:descreve a regra de negocio.
    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de Cardapio",description = "Metodo responsavel em efetuar a consulta de todos os Cardapios sem filtro")
    public ResponseEntity<List<CardapioResponse>>listarTodos(){
        return ResponseEntity.ok(cardapioService.listarTodosCardapiosTable());
    }

    // get id /cardapio/id - busca por id
    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista do Cardapio",description= "Metodo responsavel em efetuar a consulta do Cardapio por Id")
    public ResponseEntity<CardapioResponse>buscarPorId(@PathVariable Long id){
        CardapioResponse cardapioBanco = cardapioService.buscarPorId(id);
        if(cardapioBanco != null ){
            return  ResponseEntity.ok(cardapioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    // post /cardapio/ - recebe o cardapio e cria no banco.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de consulta de lista de Cardapio", description = "Metodo responsavel em efeturar a criação de novos Cardapios")
    public ResponseEntity<?> criar(@RequestBody CardapioRequest cardapio){
        try {
            CardapioResponse cardapioBanco = cardapioService.salvarCardapio(cardapio,0L);
            return ResponseEntity.ok(cardapioBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // patch id - /cardapio/id/status - atualiza neste caso o status do cardapio
    @PatchMapping ("/{id}/status")// serve para atualizar um dado só
    @Operation(summary = "Metodo de cosulta de lista de cardapio",description= "Metodo responsavel em efetuar a atualização do cardapio por Id")
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id,
                                               @RequestBody AtualizarStatusCardapio statusRequest){
        boolean atualizarCardapio = cardapioService.atualizarStatus(statusRequest.statusCardapio(),id);
        if(atualizarCardapio){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // put /cardapio/id - Aqui ele atualiza o status, nome, descrição, preco do cardapio.
    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de cardapio",description= "Metodo responsavel em efetuar a atualização de todos objetos do cardapio,filtrado por Id")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody CardapioRequest cardapio) {
        try {
            CardapioResponse cardapioBanco = cardapioService.salvarCardapio(cardapio,id);
            return ResponseEntity.ok(cardapioBanco);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // delete /cardapio/id/excluir - neste caso ele faz o delete logico: onde não exclui do banco somente altera o status.
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de Cardapio",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        var atualizarCardapio = cardapioService.atualizarStatus(EnumStatusCardapio.EXCLUIDO,id);
        if(atualizarCardapio){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}