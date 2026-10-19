package com.example.foodcommand.presentation;

import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.services.MesaService;
import com.example.foodcommand.application.DTOs.EnumStatusMesa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesa")
@Tag(name = "Mesa",description = "Grupo de APIs responsavel por controlar e consultar mesas no sistema!")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    // get /mesa -> lista todas as mesas
    //(Operation)summary: descreve a documentação da api, description:descreve a regra de negocio.
    @GetMapping
    @Operation(summary = "Metodo de consulta de mesas",description = "Metodo responsavel em efetuar a consulta de todas as mesas sem filtro")
    public ResponseEntity<List<MesaResponse>> listarTodos(){
        return ResponseEntity.ok(mesaService.listarTodasMesas());
    }

    // get/mesa/id - busca pelo id
    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de mesas",description= "Metodo responsavel em efetuar a consulta de todos as mesas por Id")
    public ResponseEntity<MesaResponse>buscarPorId(@PathVariable Long id){
        MesaResponse mesaBanco = mesaService.buscarPorId(id);
        if(mesaBanco != null ){
            return  ResponseEntity.ok(mesaBanco);
        }
        return ResponseEntity.notFound().build();
    }

    // post/mesa -> cria uma nova mesa
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de consulta de lista de mesas",description = "Metodo responsavel em efutuar a criação de novas mesas")
    public ResponseEntity<?> criar(@RequestBody MesaRequest mesa){
        try {
            MesaResponse mesaBanco = mesaService.salvarMesa(mesa,0L);
            return ResponseEntity.ok(mesaBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //pach/mesa/id/status - altera somente status neste caso.
    @PatchMapping ("/{id}/status")// serve para atualizar um dado só
    @Operation(summary = "Metodo de cosulta de lista de Mesas",description= "Metodo responsavel em efetuar a atualização das mesas por Id")
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id,
                                               @RequestBody AtualizarStatusMesa statusRequest){
        boolean atualizarMesa = mesaService.atualizarStatus(statusRequest.statusMesa(),id);
        if(atualizarMesa){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //put /mesa/id - atualiza os dado -> status, numero da mesa.
    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de Mesas",description= "Metodo responsavel em efetuar a atualizar todas as mesas,filtrado por Id")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody MesaRequest mesa) {
        try {
            MesaResponse mesaBanco = mesaService.salvarMesa(mesa,id);
            return ResponseEntity.ok(mesaBanco);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // delete /mesa/id/excluir - aqui temos exclusão logica: não apaga do banco e sim altera o status para excluido.
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de Mesas",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO o Status, filtrado por Id")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        var atualizarMesa = mesaService.atualizarStatus(EnumStatusMesa.EXCLUIDA,id);
        if(atualizarMesa){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}