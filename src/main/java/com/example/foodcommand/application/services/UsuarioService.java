package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.DTOs.EnumStatusUsuario;
import com.example.foodcommand.domain.entities.Usuario;
import com.example.foodcommand.domain.repository.UsuarioRepository;
import com.example.foodcommand.domain.valueobject.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Value("${spring.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request) {

        if (usuarioRepository.existsUsuarioByEmailAndSenha(
                request.email(),
                request.senha())) {

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminReponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if (!criarAdminRequest.secretKey().equals(secret)) {
            return new CriarAdminReponse(0L, "Usuario Salvo com sucesso");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);

        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminReponse(usuarioAdminSalvar.getId(), "Usuario Salvo com sucesso!");
    }
    //Sem id (ou id 0): cria um usuário novo, salva e devolve.
    //Com id: o findById procura o usuário.
    //Achou: o map atualiza os campos, salva e devolve o UsuarioResponse.
    //Não achou: o orElse(null) devolve null, e o controller responde 404.

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {
        if (id == null || id == 0L) {
            Usuario usuarioBanco = usuarioRepository.save(new Usuario(usuario));
            return new UsuarioResponse(usuarioBanco);
        }
        return usuarioRepository.findById(id)
                .map(usuarioBanco -> {
                    usuarioBanco.setNome(usuario.nome());
                    usuarioBanco.setCpf(new CPF(usuario.cpf()));
                    usuarioBanco.setEmail(usuario.email());
                    usuarioBanco.setSenha(usuario.senha());
                    return new UsuarioResponse(usuarioRepository.save(usuarioBanco));
                })
                .orElse(null);
    }

    // o findById procura, o map converte para UsuarioResponse se encontrou, e o orElse(null) devolve null se não encontrou.
    public UsuarioResponse buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponse::new)
                .orElse(null);
    }

    //Achou: o map troca o status, salva e devolve true, e o controller responde 200.
    //Não achou: o orElse(false) devolve false, e o controller responde 404.
    public boolean atualizarStatus(EnumStatusUsuario status,Long id){
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setStatus(status);
                    usuarioRepository.save(usuario);
                    return true;
                })
                .orElse(false);
    }

}
