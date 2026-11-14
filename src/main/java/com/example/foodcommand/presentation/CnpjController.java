package com.example.foodcommand.presentation;

import com.example.foodcommand.application.services.CnpjService;
import com.example.foodcommand.domain.valueobject.CNPJ;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cnpj")
@Tag(name = "CNPJ", description = "Consulta de dados da empresa pelo CNPJ na Receita Federal (BrasilAPI)")
public class CnpjController {

    @Autowired
    private CnpjService cnpjService;

    @GetMapping("/{cnpj}")
    @Operation(summary = "Consulta de CNPJ", description = "Busca razão social, nome fantasia e endereço da empresa pelo CNPJ")
    public ResponseEntity<?> buscarPorCnpj(@PathVariable String cnpj) {
        if (!CNPJ.isValidCnpj(cnpj)) {
            return ResponseEntity.badRequest().body("CNPJ Invalido!");
        }
        try {
            return ResponseEntity.ok(cnpjService.buscarEmpresaPorCnpj(cnpj));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
