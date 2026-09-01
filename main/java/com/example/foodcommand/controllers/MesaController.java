package com.example.foodcommand.controllers;

import com.example.foodcommand.DTOs.AtualizarStatusMesa;
import com.example.foodcommand.entities.EnumStatusMesa;
import com.example.foodcommand.entities.Mesa;
import com.example.foodcommand.repository.MesaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mesa")
@Tag(name = "Mesa",description = "Grupo de APIs responsavel por controlar e consultar mesas no sistema!")
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    @GetMapping
    @Operation(summary = "Metodo de consulta de mesas",description = "Metodo responsavel em efetuar a consulta de todas as mesas sem filtro")
    private ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(mesaRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de mesas",description= "Metodo responsavel em efetuar a consulta de todos as mesas por Id")
    public ResponseEntity<Mesa>buscarPorId(@PathVariable Long id){
        Mesa usuarioBanco = mesaRepository.findById(id).orElse(null);
        if(usuarioBanco != null ){
            return  ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de consulta de lista de mesas",description = "Metodo responsavel em efutuar a criação de novas mesas")
    public ResponseEntity<Mesa>criar(@RequestBody Mesa mesa){
        var mesaBanco = mesaRepository.save(mesa);
        return ResponseEntity.ok(mesaBanco);
    }

    @PatchMapping ("/{id}/status")// serve para atualizar um dado só
    @Operation(summary = "Metodo de cosulta de lista de Mesas",description= "Metodo responsavel em efetuar a atualização das mesas por Id")
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusMesa statusRequest){

        Mesa mesaBanco = mesaRepository.findById(id).orElse(null);
        if(mesaBanco != null ){
            mesaBanco.setStatusMesa(statusRequest.statusMesa());
            mesaRepository.save((mesaBanco));
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de Mesas",description= "Metodo responsavel em efetuar a atualizar todas as mesas,filtrado por Id")
    public ResponseEntity<Mesa> atualizar(@PathVariable Long id,@RequestBody Mesa mesa) {
        try {
            Mesa mesaBanco = mesaRepository.findById(id).orElse(null);
            if (mesaBanco != null) {
                mesaBanco.setStatusMesa(mesa.getStatusMesa());
                mesaBanco.setNumero(mesa.getNumero());
                mesaRepository.save((mesaBanco));
                return ResponseEntity.ok().build();

            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de Mesas",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO o Status, filtrado por Id")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Mesa mesaBanco = mesaRepository.findById(id).orElse(null);
        if(mesaBanco != null ){
            mesaBanco.setStatusMesa(EnumStatusMesa.EXCLUIDA);
            mesaRepository.save((mesaBanco));
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
