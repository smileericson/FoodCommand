package com.example.foodcommand.controllers;

import com.example.foodcommand.DTOs.AtualizarStatusRequest;
import com.example.foodcommand.entities.EnumStatusUsuario;
import com.example.foodcommand.entities.Usuario;
import com.example.foodcommand.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Indica que a classe é um controller REST.
// Recebe requisições HTTP e retorna respostas, normalmente em JSON.
@RequestMapping("/usuarios")
// Define o caminho-base de todas as rotas desta classe.
// Exemplo: /usuarios, /usuarios/1.

@Tag(name="Usuarios", description = "Grupo de APIs responsavel por controlar a estrutura e cosulta de usuário do sistema!")
// Agrupa e documenta os endpoints no Swagger/OpenAPI.
public class UsuarioController {

    @Autowired
    // O Spring injeta uma instância do UsuarioRepository.
    // Permite consultar, salvar e acessar os usuários no banco.
    private UsuarioRepository usuarioRepository;

    // =====================================================
    // 1. LISTAR TODOS OS USUÁRIOS
    // =====================================================

    @GetMapping
    // Define um endpoint HTTP GET no caminho /usuarios.


    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a consulta de todos os usuarios sem filtro")
    // Ela serve exclusivamente para descrever a operação na documentação da API.
    // vai ficar ao lado do usuario vai ficar tipo um titulo,a description é a mais importante vai descrever a regra de negocio.
    public ResponseEntity<?> listarTodos(){

        List<Usuario> usuarios = List.of(new Usuario(1L,
                "Ericson",
                "08319706939",
                "123456",
                "smileericson@gmail.com",
                EnumStatusUsuario.ATIVO));
        // Cria uma lista de exemplo com um usuário.
        // Nesta implementação, a variável não é utilizada na resposta.

        return ResponseEntity.ok(usuarioRepository.findAll());
        // Consulta todos os usuários por meio do Repository
        // e retorna a lista com HTTP 200 OK.
    }

    //acesso, saida, nome , entrada

    // =====================================================
    // 2. BUSCAR USUÁRIO POR ID
    // =====================================================

    @GetMapping("/{id}")
    // Define um GET com um parâmetro variável na URL.
    // Exemplo: GET /usuarios/1.
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a consulta de todos os usuarios por Id")
    // Ela serve exclusivamente para descrever a operação na documentação da API.
    // vai ficar ao lado do usuario vai ficar tipo um titulo,a description é a mais importante vai descrever a regra de negocio.
    public ResponseEntity<Usuario>buscarPorId(@PathVariable Long id){
        // Recebe o ID informado na URL.
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        // Busca o usuário pelo ID.
        // Se não encontrar, retorna null.
        if(usuarioBanco != null ){
            // Verifica se o usuário foi encontrado.
            return  ResponseEntity.ok(usuarioBanco);
            // Retorna o usuário encontrado com HTTP 200 OK.
        }
        return ResponseEntity.notFound().build();
        // Caso não encontre, retorna HTTP 404 Not Found.

    }

    @PostMapping
    // Define um endpoint HTTP POST no caminho /usuarios.
    //Criar usuarios

    @ResponseStatus(HttpStatus.CREATED)
    // Indica o status HTTP 201 Created.
    // Observação: o ResponseEntity abaixo também define
    // explicitamente um status HTTP para a resposta.
    // copiar e altera para criação.
    @Operation(summary = "Metodo de cosulta de lista de usuários",description = "Metodo responsavel em efetuar a criação de novos usuarios")
    // Ela serve exclusivamente para descrever a operação na documentação da API.
    // vai ficar ao lado do usuario vai ficar tipo um titulo,a description é a mais importante vai descrever a regra de negocio.
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){
        // Converte o corpo da requisição, normalmente JSON,
        // em um objeto da entidade Usuario.
        var usuarioBanco =  usuarioRepository.save(usuario);
        // Salva o usuário no banco de dados.
        // O objeto retornado representa o usuário salvo.
        return ResponseEntity.ok(usuarioBanco);
        // Retorna o usuário salvo com HTTP 200 OK.
    }
    // =====================================================
    // 4. ATUALIZAR SOMENTE O STATUS DO USUÁRIO
    // =====================================================

    @PatchMapping ("/{id}/status")
    // Define um PATCH para atualizar parcialmente o recurso.
    // Neste caso, a atualização é somente do status

    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização de todos os usuarios por Id")
    // Ela serve exclusivamente para descrever a operação na documentação da API.
    // vai ficar ao lado do usuario vai ficar tipo um titulo,a description é a mais importante vai descrever a regra de negocio.
    public ResponseEntity<Void>atualizarStatus(@PathVariable Long id,
                                               // Busca o usuário no banco pelo ID.
                                               @RequestBody AtualizarStatusRequest statusRequest){
                                                // Recebe o novo status no corpo da requisição.

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        // Busca o usuário no banco pelo ID.
        if(usuarioBanco != null ){
            // Verifica se o usuário existe.
            usuarioBanco.setStatus(statusRequest.status());
            // Atualiza o status do usuário com o valor recebido.
            usuarioRepository.save((usuarioBanco));
            // Persiste a alteração no banco de dados
            return  ResponseEntity.ok().build();
            // Retorna HTTP 200 OK sem corpo de resposta.
        }
        return ResponseEntity.notFound().build();
        // Se não encontrar o usuário, retorna HTTP 404.

    }
    // =====================================================
    // 5. ATUALIZAR OS DADOS DO USUÁRIO
    // =====================================================

    @PutMapping("/{id}")
    // Define um endpoint HTTP PUT para o ID informado.
    // Exemplo: PUT /usuarios/1.
    // Atualiza os dados do usuario
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização de todos os usuarios,filtrado por Id")
    // Ela serve exclusivamente para descrever a operação na documentação da API.
    // vai ficar ao lado do usuario vai ficar tipo um titulo,a description é a mais importante vai descrever a regra de negocio.
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,
                                             // Recebe o ID do usuário que será atualizado.
                                             @RequestBody Usuario usuario) {
                                            // Recebe os novos dados no corpo da requisição.
        try {
            // Trata possíveis RuntimeExceptions dentro do bloco.
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            // Busca o usuário que já está cadastrado.
            if (usuarioBanco != null) {
                // Só atualiza se o usuário existir.
                usuarioBanco.setStatus(usuario.getStatus());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setCpf(usuario.getCpf());
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioBanco.setSenha(usuario.getSenha());
                //Acima atualiza cada situação
                usuarioRepository.save((usuarioBanco));
                //Salva as alterações no banco.
                return ResponseEntity.ok().build();
                // Retorna HTTP 200 OK.
                // O build() não inclui um corpo na resposta.

            }
            return ResponseEntity.notFound().build();
            // Se não encontrar o usuário, retorna HTTP 404.
        }catch (RuntimeException e){
            throw new RuntimeException(e);
            // Lança novamente a exceção recebida.
        }
    }
    // =====================================================
    // 6. EXCLUIR USUÁRIO
    // =====================================================

    @DeleteMapping("/{id}/excluir")
    // Define um DELETE no caminho /usuarios/{id}/excluir.
    //exclusão lógica altera o status; exclusão física remove o registro do banco.
    @Operation(summary = "Metodo de cosulta de lista de usuários",description= "Metodo responsavel em efetuar a atualização para EXCLUIDO, filtrado por Id")
    // Ela serve exclusivamente para descrever a operação na documentação da API.
    // vai ficar ao lado do usuario vai ficar tipo um titulo,a description é a mais importante vai descrever a regra de negocio
    public ResponseEntity<Void> excluir(@PathVariable Long id){
                                        // Recebe o ID do usuário pela URL.

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);// Busca o usuário pelo ID.
        if(usuarioBanco != null ){// Verifica se o usuário existe.
            usuarioBanco.setStatus(EnumStatusUsuario.EXCLUIDO);
            // Altera o status para EXCLUIDO.
            // Não remove fisicamente o registro do banco.
            usuarioRepository.save((usuarioBanco));
            // Salva a alteração do status.
            return  ResponseEntity.ok().build();
            // Retorna HTTP 200 OK sem corpo.
        }
        return ResponseEntity.notFound().build();
        // Se o usuário não existir, retorna HTTP 404.
    }

}
