package com.example.foodcommand.application.services;

import com.example.foodcommand.application.DTOs.LoginRequest;
import com.example.foodcommand.application.DTOs.LoginResponse;
import com.example.foodcommand.application.DTOs.UsuarioResponse;
import com.example.foodcommand.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

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
}
