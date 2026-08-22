package com.example.foodcommand.controllers;

import com.example.foodcommand.entities.Mesa;
import com.example.foodcommand.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mesa")
public class MesaController {

    private MesaRepository mesaRepository;

    @Autowired
    private ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(mesaRepository.findAll());
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Mesa>criar(@RequestBody Mesa mesa){
        var mesaBanco = mesaRepository.save(mesa);
        return ResponseEntity.ok(mesaBanco);
    }
}
