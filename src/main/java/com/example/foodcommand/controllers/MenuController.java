package com.example.foodcommand.controllers;

import com.example.foodcommand.DTOs.AtualizarStatusMenu;
import com.example.foodcommand.entities.EnumStatusMenu;
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

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista do Menu",description= "Metodo responsavel em efetuar a consulta do menu por Id")
    public ResponseEntity<Menu>buscarPorId(@PathVariable Long id){
        Menu menuBanco = menuRepository.findById(id).orElse(null);
        if(menuBanco != null ){
            return  ResponseEntity.ok(menuBanco);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de consulta de lista de menu", description = "Metodo responsavel em efeturar a criação de novos menus")
    public ResponseEntity<Menu>criar(@RequestBody Menu menu){
        var menuBanco= menuRepository.save(menu);
        return ResponseEntity.ok(menuBanco);
    }
    @PatchMapping ("/{id}/status")// serve para atualizar um dado só
    @Operation(summary = "Metodo de cosulta de lista de Menu",description= "Metodo responsavel em efetuar a atualização do menu por Id")
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusMenu statusRequest){

        Menu menuBanco = menuRepository.findById(id).orElse(null);
        if(menuBanco != null ){
            menuBanco.setStatusMenu(statusRequest.statusMenu());
            menuRepository.save((menuBanco));
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de Menu",description= "Metodo responsavel em efetuar a atualização de todos objetos do menu,filtrado por Id")
    public ResponseEntity<Menu> atualizar(@PathVariable Long id,@RequestBody Menu menu) {
        try {
            Menu menuBanco = menuRepository.findById(id).orElse(null);
            if (menuBanco != null) {
                menuBanco.setStatusMenu(menu.getStatusMenu());
                menuBanco.setProduto(menu.getProduto());
                menuBanco.setDescricao(menu.getDescricao());
                menuBanco.setPreco(menu.getPreco());
                menuRepository.save((menuBanco));
                return ResponseEntity.ok().build();

            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de menu",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Menu menuBanco = menuRepository.findById(id).orElse(null);
        if(menuBanco != null ){
            menuBanco.setStatusMenu(EnumStatusMenu.EXCLUIDO);
            menuRepository.save((menuBanco));
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
