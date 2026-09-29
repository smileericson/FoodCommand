package com.example.foodcommand.configuration;


import com.example.foodcommand.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// Registra o JwtFilter no contexto do Spring,
// permitindo que ele seja reconhecido como um componente da aplicação.

public class JwtFilter extends OncePerRequestFilter {
    // Declara a classe JwtFilter, responsável por interceptar
    // as requisições HTTP e realizar a validação do token JWT.

    @Autowired
    // Realiza a injeção de dependência do TokenService,
    // permitindo utilizar seus métodos sem instanciar o serviço manualmente.
    private TokenService tokenService;


    @Override
    // Indica que o método abaixo sobrescreve um método
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Método principal do filtro.
        // Recebe a requisição, a resposta e a cadeia de filtros.
        // Permite interceptar a requisição antes de ela chegar ao controller.

        String uri = request.getRequestURI();
        // Verifica se a requisição corresponde a alguma rota
        // aqui vamos colocar as rotas que vamos autorizar para validar o token
        if(uri.startsWith("/swagger-ui")
        || uri.startsWith("/v2/api-docs")
        || uri.startsWith("/v3/api-docs")
        || uri.startsWith("/swagger-resources")
        || uri.startsWith("webjars")
        || uri.startsWith("/auth/login")
        || uri.startsWith("/")
        ){
            filterChain.doFilter(request,response);
            // Encaminha a requisição para os próximos filtros
            return;
            // Encerra a execução do método para não continuar
        }

        String authHeader = request.getHeader("Authorization");
        // É nele que o cliente normalmente envia o token JWT.

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            // Verifica se o cabeçalho existe e se começa
            String token = authHeader.replace("Bearer ","");
            // Remove o prefixo Bearer do cabeçalho, deixando somente o JWT
            try{

                var jwtValidador = tokenService.vericarToken(token);
                // Chama o TokenService para validar o token.
                System.out.println(jwtValidador.getSubject());
                // Exibe no console o subject do token validado,

            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // Define o status HTTP 401 (Unauthorized)
                response.getWriter().println("Token inválido");
                return;
            }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Token inválido");
            return;
        }

        filterChain.doFilter(request,response);

    }
}

//Interceptar as requisições e verificar o JWT antes de encaminhar as requisições protegidas.