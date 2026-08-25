package com.example.foodcommand.controllers;

import com.example.foodcommand.entities.Menu;
import com.example.foodcommand.repository.MenuRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@Tag(name="Menu",description = "Grupo de APIs responsavel por controlar a estrutura e consultar o menu do sistema")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;


    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de Menu",description = "Metodo responsavel em efetuar a consulta de todos os menus sem filtro")
    public ResponseEntity<?>listarTodos(){
        return ResponseEntity.ok(menuRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de consulta de lista de menu", description = "Metodo responsavel em efeturar a criação de novos menus")
    public ResponseEntity<Menu>criar(@RequestBody Menu menu){
        var menuBanco= menuRepository.save(menu);
        return ResponseEntity.ok(menuBanco);
    }
}
