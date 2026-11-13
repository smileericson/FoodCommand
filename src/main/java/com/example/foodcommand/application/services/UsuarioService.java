package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.application.DTOs.EnumStatusUsuario;
import com.example.foodcommand.domain.entities.Empresa;
import com.example.foodcommand.domain.entities.Usuario;
import com.example.foodcommand.domain.repository.EmpresaRepository;
import com.example.foodcommand.domain.repository.UsuarioRepository;
import com.example.foodcommand.domain.valueobject.CPF;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Value("${spring.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmpresaRepository empresaRepository;

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

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return usuarioRepository.findAll()
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
        }

        return usuarioRepository.getUsuariosByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }
    @Transactional
    public CriarEmpresaDonoResponse criarEmpresaDono(CriarEmpresaDonoRequest criarEmpresaDonoRequest) {

        if (!criarEmpresaDonoRequest.secretKey().equals(secret)) {
           throw new RuntimeException("Scretkey inválida");
        }

        Empresa empresaSalvar = empresaRepository.save(new Empresa(criarEmpresaDonoRequest));

        Usuario usuarioEmpresaDonoSalvar = new Usuario(criarEmpresaDonoRequest);
        usuarioEmpresaDonoSalvar.setEmpresa(empresaSalvar);

        usuarioRepository.save(usuarioEmpresaDonoSalvar);

        return new CriarEmpresaDonoResponse(usuarioEmpresaDonoSalvar.getId(), "Empresa e Dono salvos com sucesso!");
    }

    public CriarUsuarioAdminSistemaResponse criarUsuarioAdminSistema(CriarUsuarioAdminSistemaRequest criarUsuarioAdminSistemaRequest) {
        if (!criarUsuarioAdminSistemaRequest.secretKey().equals(secret)) {
            throw new RuntimeException("SecretKey inválida");
        }
        Usuario usuarioAdminSistema = new Usuario(criarUsuarioAdminSistemaRequest);
        usuarioRepository.save(usuarioAdminSistema);

        return new CriarUsuarioAdminSistemaResponse(usuarioAdminSistema.getId(), "Usuario Admin do Sistema salvo com sucesso");
    }

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    // NOVO
    // Aqui faz: busco o usuário pelo id, mas só devolvo se ele for da MESMA empresa de quem está logado.
    // Se quem está logado não tem empresa (empresa_id null = usuário Admin do Sistema), devolvo qualquer um.
    // Se for de outra empresa, devolvo vazio -> quem chamou trata como "não encontrado" (404).
    // É o mesmo filtro que já existia no buscarPorId, agora reaproveitado no alterar e no status.
    private Optional<Usuario> buscarUsuarioDaEmpresa(Long id) {
        var usuarioLogado = getUsuarioLogado();
        return usuarioRepository.findById(id)
                .filter(u -> usuarioLogado.empresa_id() == null
                        || (u.getEmpresa() != null && u.getEmpresa().getId().equals(usuarioLogado.empresa_id())));
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {
        if (id == null || id == 0L) {
            Usuario novoUsuario = new Usuario(usuario);
            var usuarioLogado = getUsuarioLogado();
            if (usuarioLogado.empresa_id() != null) {
                novoUsuario.setEmpresa(empresaRepository.findById(usuarioLogado.empresa_id()).orElse(null));
            }
            Usuario usuarioBanco = usuarioRepository.save(novoUsuario);
            return new UsuarioResponse(usuarioBanco);
        }
        return buscarUsuarioDaEmpresa(id)
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
        return buscarUsuarioDaEmpresa(id)
                .map(UsuarioResponse::new)
                .orElse(null);
    }

    //Achou: o map troca o status, salva e devolve true, e o controller responde 200.
    //Não achou: o orElse(false) devolve false, e o controller responde 404.
    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {
        // ALTERADO: era usuarioRepository.findById(id)
        // Aqui faz: só mudo o status (e excluo) se o usuário for da minha empresa; senão devolvo false -> 404
        return buscarUsuarioDaEmpresa(id)
                .map(usuario -> {
                    usuario.setStatus(status);
                    usuarioRepository.save(usuario);
                    return true;
                })
                .orElse(false);
    }

    public UsuarioResponse BuscarUsuarioLogado() {
        try {
            var usuarioLogado = getUsuarioLogado();
            return usuarioRepository.findById(usuarioLogado.id())
                    .stream()
                    .map(UsuarioResponse::new)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar buscar usuario logado");
        }
    }
}
