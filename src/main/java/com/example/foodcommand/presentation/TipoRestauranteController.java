package com.example.foodcommand.presentation;

import com.example.foodcommand.application.DTOs.CriarTipoRestauranteRequest;
import com.example.foodcommand.application.DTOs.CriarTipoRestauranteResponse;
import com.example.foodcommand.application.DTOs.TipoRestauranteResponse;
import com.example.foodcommand.application.services.TipoRestauranteService;
import com.example.foodcommand.domain.repository.TipoRestauranteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiporestaurante")
@Tag(name= "TipoRestaurante",description = "Grupo de APIs resposanavel por controlar tipo de restaurante, podendo cadastrar e atualizar!")

public class TipoRestauranteController {

    @Autowired
    private TipoRestauranteRepository tipoRestauranteRepository;

    @Autowired
    private TipoRestauranteService tipoRestauranteService;

    @GetMapping
    @Operation(summary = "Metodo de consulta de tipos de restaurante",description = "Método resposavel por efetuar a consulta de todos os tipos de restaurante")
    public ResponseEntity<List<TipoRestauranteResponse>> listarTodos(){
        return ResponseEntity.ok(tipoRestauranteService.listarTodosTiposRestauranteTable());
    }

    @PostMapping("/criar")
    @Operation(summary = "Metodo de criar de tipos de restaurante",description = "Método resposavel por efetuar a criação de todos os tipos de restaurante")
    public ResponseEntity<CriarTipoRestauranteResponse> criarTipoRestaurante(@RequestBody CriarTipoRestauranteRequest criarTipoRestauranteRequest){

        try{
            CriarTipoRestauranteResponse respostaSalvar = tipoRestauranteService.criarTipoRestaurante(criarTipoRestauranteRequest);
            return ResponseEntity.ok(respostaSalvar);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }




}
