package com.example.foodcommand.presentation;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.services.PedidoService;
import com.example.foodcommand.application.DTOs.EnumStatusPedido;
import com.example.foodcommand.application.services.TokenService;
import com.example.foodcommand.domain.entities.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1 - indica classe, recebe requisição do http e retorna resposta api
// 2 - defini o endpoint do controller
// 3- injeta o pedidorepository é responsavel pelo acesso dos pedidos ao banco
// 4- injeta pedidoService é utilizado para executar a logica relacionada ao pedido
// 5 - define o endpoint GET para/pedidos é utilizado para consultar a lista de pedidos

@RestController
@RequestMapping("/pedidos")
@Tag(name="Pedidos",description = "Grupo de APIs resposanavel por controlar a estrutura e consulta de Pedidos do sistema!")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // PASSO 4.3 - usado para descobrir quem é o garçom logado a partir do token
    @Autowired
    private TokenService tokenService;

    @GetMapping
    //retorna uma resposta do http contendo a lista de pedidoresponse
    // chama o metodo ...table depois service e retorna do http reponseentity com http 200 e a lista.
    public ResponseEntity<List<PedidoResponse>> listarTodos(){
        return ResponseEntity.ok(pedidoService.listarTodosPedidosTable());
    }

    //1 Define o get como variavel exemplo /pedidos/1
    //2 - summary: descreve a documentação da api, description:descreve a regra de negocio.
    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a consulta de todos os pedidos por Id")
    public ResponseEntity<PedidoResponse>buscarPorId(@PathVariable Long id){
        PedidoResponse pedidoBanco = pedidoService.buscarPorId(id);
        if(pedidoBanco != null ){
            return  ResponseEntity.ok(pedidoBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //1 - define o enpoint caminho/pedido e cria um pedido.
    //2 - indica o status 201 created
    //3 -  Serve para descrever a documentação, summary: descreve a documentação description a regra de negocio
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

    // PASSO 4.3 - POST /pedidos/mesa/{mesaId}/abrir - o garçom logado abre o atendimento de uma mesa livre
    // mesaId vem da URL e o garçom vem do token (cabeçalho), nunca do JSON
    @PostMapping("/mesa/{mesaId}/abrir")
    @Operation(summary = "Abrir atendimento", description = "O garçom logado abre o atendimento de uma mesa livre")
    public ResponseEntity<?> abrirAtendimento(@PathVariable Long mesaId,
                                              @RequestHeader("Authorization") String authorization) {
        try {
            // descobre o garçom pelo token
            Usuario garcomLogado = tokenService.buscarUsuarioLogado(authorization);

            // abre o atendimento gravando a mesa e o garçom no pedido
            PedidoResponse pedido = pedidoService.abrirAtendimento(mesaId, garcomLogado);
            return ResponseEntity.ok(pedido);

        } catch (JWTVerificationException e) {
            // token falso, alterado ou expirado -> 401 (não autenticado)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");

        } catch (Exception e) {
            // erros de regra (sem token, mesa não encontrada, mesa não livre) -> 400 com a mensagem
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // PASSO 5.4 - POST /pedidos/{id}/itens - adiciona um prato ao pedido
// id do pedido vem da URL, prato e quantidade vêm do corpo, garçom vem do token
    @PostMapping("/{id}/itens")
    @Operation(summary = "Adicionar item", description = "Adiciona um item ao pedido. Somente o garçom que abriu o atendimento")
    public ResponseEntity<?> adicionarItem(@PathVariable Long id,
                                           @RequestBody AdicionarItemRequest itemRequest,
                                           @RequestHeader("Authorization") String authorization) {
        try {
            Usuario garcomLogado = tokenService.buscarUsuarioLogado(authorization);
            PedidoResponse pedido = pedidoService.adicionarItem(id, itemRequest.cardapioId(), itemRequest.quantidade(), garcomLogado);
            return ResponseEntity.ok(pedido);
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PASSO 5.4 - PATCH /pedidos/{id}/pedir-conta - mesa fica AGUARDANDO_FECHAMENTO
    @PatchMapping("/{id}/pedir-conta")
    @Operation(summary = "Pedir a conta", description = "Coloca a mesa aguardando fechamento. Somente o garçom que abriu o atendimento")
    public ResponseEntity<?> pedirConta(@PathVariable Long id,
                                        @RequestHeader("Authorization") String authorization) {
        try {
            Usuario garcomLogado = tokenService.buscarUsuarioLogado(authorization);
            return ResponseEntity.ok(pedidoService.pedirConta(id, garcomLogado));
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PASSO 5.4 - PATCH /pedidos/{id}/fechar-conta - pedido FECHADO e mesa LIVRE
    @PatchMapping("/{id}/fechar-conta")
    @Operation(summary = "Fechar a conta", description = "Fecha o pedido e libera a mesa. Somente o garçom que abriu o atendimento")
    public ResponseEntity<?> fecharConta(@PathVariable Long id,
                                         @RequestHeader("Authorization") String authorization) {
        try {
            Usuario garcomLogado = tokenService.buscarUsuarioLogado(authorization);
            return ResponseEntity.ok(pedidoService.fecharConta(id, garcomLogado));
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //1 - Atualiza parcialmente um recurso, no caso somente o status
    //2 - Descreve a documentação da api, summary descreve e description a regra de negocio.

    @PatchMapping ("/{id}/status")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização de todos os pedidos por Id")

    // pachvariable busca o id, requestbory recebe o novo status da requisicao
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id,
                                               @RequestBody AtualizarStatusPedido statusRequest){
        boolean atualizarPedido = pedidoService.atualizarStatus(statusRequest.statusPedido(),id);
        if(atualizarPedido){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    //1 - define o endpint para id informada, atualiza os dados do pedido completo
    // 2 - descreve a documentação da api, summary descrição, description regra de negocio.

    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização de todos os pedidos,filtrado por Id")

    // pathvariabele recebe o id pedido,
    // requestBody converte o JSON enviado no corpo da requisição em um objeto PedidoRequest
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody PedidoRequest pedido) {
        try {
            PedidoResponse pedidoBanco = pedidoService.salvarPedido(pedido,id);
            return ResponseEntity.ok(pedidoBanco);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //1 define o caminho do delete, exclusão logica altera o status nosso caso, se fosse exclusão fisica removeria o registro do banco
    // 2 serve para descrever a documentação da api, summary descrição , description regra de negocio

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de pedidos",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")

    // patchvariable recebe o id do pedido.
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarPedido = pedidoService.atualizarStatus(EnumStatusPedido.EXCLUIDO,id);
        if(atualizarPedido){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}