package com.example.foodcommand.presentation;

import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.services.UsuarioService;
import com.example.foodcommand.application.DTOs.EnumStatusUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1 - indica classe, recebe requisiçaõ do http e retorna resposta api
// 2 - defini o endpoint do controller
// 3- injeta o usuariorepository é reposavel pelo acesso do usuarios ao banco
// 4- injeta usuarioSrvice é utilizado para executar a logica relacionada ao usuario
// 5 - define o endpoint GET para/usuarios é utilizado para consultar a lista de usuarios

@RestController
@RequestMapping("/usuarios")
@Tag(name="Usuarios",description = "Grupo de APIs resposanavel por controlar a estrutura e consulta de Usuarios do sistema!")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    //retorna uma resposta do http contendo a lista de usuarioresponce
    // chama o metodo ...table depois service e retorna do http reponseentity com http 200 e a lista.
   public ResponseEntity<List<UsuarioResponse>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }

    @PostMapping("/admin")
    @Operation(summary = "Método de criação de usuários ADMS!", description = "Método reponsável em efetuar a criação de novos usuários ADMS!")
    public ResponseEntity<CriarAdminReponse>criarAdmin(@RequestBody CriarAdminRequest criarAdminRequest) {
        try {
            CriarAdminReponse repostaSalvar = usuarioService.criarAdmin(criarAdminRequest);
            return ResponseEntity.ok(repostaSalvar);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //1 Define o get como variavel exemplo /usuarios/1
    //2 - summary: descreve a documentação da api, description:descreve a regra de negocio.
    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a consulta de todos os usuarios por Id")
    public ResponseEntity<UsuarioResponse>buscarPorId(@PathVariable Long id){
        UsuarioResponse usuarioBanco = usuarioService.buscarPorId(id);
        if(usuarioBanco != null ){
            return  ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //1 - define o enpoint caminho/usuario e cria um usuario.
    //2 - indica o status 201 created
    //3 -  Serve para descrever a documentação, summary: descreve a documentação description a regra de negocio
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de cosulta de lista de usuários",description = "Metodo responsavel em efetuar a criação de novos usuarios")
    public ResponseEntity<?> criar(@RequestBody UsuarioRequest usuario){
       try {
           UsuarioResponse usuarioBanco =  usuarioService.salvarUsuario(usuario,0L);
           return ResponseEntity.ok(usuarioBanco);
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    //1 - Atualiza parcialmente um recurso, no caso somente o status
    //2 - Descreve a documentação da api, summary descreve e description a regra de negocio.

    @PatchMapping ("/{id}/status")
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização de todos os usuarios por Id")

    // pachvariable busca o id, requestbory recebe o novo status da requisicao
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id,
                                               @RequestBody AtualizarStatusRequest statusRequest){
        boolean atualizarUsuario = usuarioService.atualizarStatus(statusRequest.status(),id);
        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    //1 - define o endpint para id informada, atualiza os dados do usuario completo
    // 2 - descreve a documentação da api, summary descrição, description regra de negocio.

    @PutMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização de todos os usuarios,filtrado por Id")

    // pathvariabele recebe o id usuario,
    // requestBody converte o JSON enviado no corpo da requisição em um objeto UsuarioRequest
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                             @RequestBody UsuarioRequest usuario) {
       try {
           UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario,id);
           return ResponseEntity.ok(usuarioBanco);
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    //1 define o caminho do delete, exclusão logica altera o status nosso caso, se fosse exclusão fisica removeria o registro do banco
    // 2 serve para descrever a documentação da api, summary descrição , description regra de negocio

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")

    // patchvariable recebe o id do usuario.
    public ResponseEntity<Void> excluir(@PathVariable Long id){

       var atualizarUsuario = usuarioService.atualizarStatus(EnumStatusUsuario.EXCLUIDO,id);
       if(atualizarUsuario){
           return ResponseEntity.ok().build();
       }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/buscarusuariologado")
    @Operation(summary = "Usuario logado", description="Usuario logado")
    public ResponseEntity<UsuarioResponse>buscarUsuarioLogado(){
        return ResponseEntity.ok(usuarioService.BuscarUsuarioLogado());
    }

}
