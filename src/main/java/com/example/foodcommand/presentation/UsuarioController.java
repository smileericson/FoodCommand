package com.example.foodcommand.presentation;

import com.example.foodcommand.application.DTOs.AtualizarStatusRequest;
import com.example.foodcommand.application.DTOs.UsuarioResponse;
import com.example.foodcommand.application.services.UsuarioService;
import com.example.foodcommand.domain.entities.EnumStatusUsuario;
import com.example.foodcommand.domain.entities.Usuario;
import com.example.foodcommand.domain.repository.UsuarioRepository;
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


public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping

    //retorna uma resposta do http contendo a lista de usuarioresponce
    // chama o metodo ...table dp service e retorna do http reponseentity com http 200 e a lista.
   public ResponseEntity<List<UsuarioResponse>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }

    //1 Define o get como variavel exemplo /usuarios/1
    //2 - summary: descreve a documentação da api, description:descreve a regra de negocio.
    @GetMapping("/{id}")
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a consulta de todos os usuarios por Id")

    //recebe o id informado na URL
    // recebe o usuario pelo id se não encontrar retorna null
    // if verifica se o usuario foi encontrado, retorna 200, caso não encontre returna 404 not found
    public ResponseEntity<Usuario>buscarPorId(@PathVariable Long id){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
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

    // converte parte do requestbory o JSON em um objeto da entidade Usuario.
    // salva o usuario no banco .save
    // retur retorna http 200

    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){
        var usuarioBanco =  usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuarioBanco);
    }

    //1 - Atualiza parcialmente um recurso, no caso somente o status
    //2 - Descreve a documentação da api, summary descreve e description a regra de negocio.

    @PatchMapping ("/{id}/status")
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização de todos os usuarios por Id")

    // pachvariable busca o id, requestbory recebe o novo status da requisicao
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id,
                                               @RequestBody AtualizarStatusRequest statusRequest){
        // busca o usuario por id findbyid, verifica se é null orElse, verifica se existe !=
        // atualiza o status do usuario com valor recebido se existir
        // persiste a alteração no banco .save
        // retorna http 200
        // retorna2 se não encontrar http 404
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);

        if(usuarioBanco != null ){
            usuarioBanco.setStatus(statusRequest.status());
            usuarioRepository.save((usuarioBanco));
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    //1 - define o endpint para id informada, atualiza os dados do usuario completo
    // 2 - descreve a documentação da api, summary descrição, description regra de negocio.

    @PutMapping("/{id}")

    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização de todos os usuarios,filtrado por Id")

    // pathvariabele recebe o id usuario, requestbody recebe o novo dados no corpo da requisição.
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,
                                             @RequestBody Usuario usuario) {

        // aqui tratase as possiveis runtimeexceptions dentro do bloco
        // busca o usuario cadastrado findbyid ou orElse se é null
        // só atualiza se o usuario for != de null "existir"
        // os atributos
        // .save salva as atribuições alteradads
        // retorna http200
        // retorna 2 se usuario não encontrado http 404
        // cath retorna novamente a exceção recebida
        try {

            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if (usuarioBanco != null) {

                usuarioBanco.setStatus(usuario.getStatus());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setCpf(usuario.getCpf());
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioBanco.setSenha(usuario.getSenha());

                usuarioRepository.save((usuarioBanco));
                return ResponseEntity.ok().build();


            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);

        }
    }
    //1 define o caminho do delete, exclusão logica altera o status nosso caso, se fosse exclusão fisica removeria o registro do banco
    // 2 serve para descrever a documentação da api, summary descrição , description regra de negocio

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")

    // patchvariable recebe o id do usuario.
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        // busca o ide findbyid, oreslse se é nul
        // verifica se o usuario =! de null se ele existe

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null ){

            // altera o statuso para excluido, sem remover o registro do banco
            // salva a alteração do status
            // retorna http 200
            // notfound, se o usuario não existir retorna http 404
            usuarioBanco.setStatus(EnumStatusUsuario.EXCLUIDO);
            usuarioRepository.save((usuarioBanco));

            return  ResponseEntity.ok().build();

        }
        return ResponseEntity.notFound().build();

    }

}
