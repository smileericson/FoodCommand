package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.*;
import com.example.foodcommand.domain.entities.Usuario;
import com.example.foodcommand.domain.repository.UsuarioRepository;
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

    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request){

        if (usuarioRepository.existsUsuarioByEmailAndSenha(
                request.email(),
                request.senha())){

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }
    public List<UsuarioResponse> listarTodosUsuariosTable(){
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminReponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)){
            return new CriarAdminReponse(0L,"Usuario Salvo com sucesso");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);

        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminReponse(usuarioAdminSalvar.getId(),"Usuario Salvo com sucesso!");
    }
}
