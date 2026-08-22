package com.example.foodcommand.controllers;

import com.example.foodcommand.entities.Menu;
import com.example.foodcommand.repository.MenuRepository;
import com.example.foodcommand.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Menu>criar(@RequestBody Menu menu){
        var menuBanco= menuRepository.save(menu);
        return ResponseEntity.ok(menuBanco);
    }
}
