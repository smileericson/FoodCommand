package com.example.foodcommand.controllers;

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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de consulta de lista de mesas",description = "Metodo responsavel em efutuar a criação de novas mesas")
    public ResponseEntity<Mesa>criar(@RequestBody Mesa mesa){
        var mesaBanco = mesaRepository.save(mesa);
        return ResponseEntity.ok(mesaBanco);
    }
}
