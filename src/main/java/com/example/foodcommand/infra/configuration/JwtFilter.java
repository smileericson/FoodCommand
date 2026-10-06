package com.example.foodcommand.infra.configuration;


import com.example.foodcommand.application.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// 1-  Registra o JwtFilter no contexto do Spring
// 2 -Realiza a injeção de dependência do TokenService,permitindo utilizar seus métodos sem instanciar manualmente
// 3- Indica que o método abaixo sobrescreve um método

@Component



public class JwtFilter extends OncePerRequestFilter {
    // Declara a classe JwtFilter, responsável por interceptar
    // as requisições HTTP e realizar a validação do token JWT.

    @Autowired
    private TokenService tokenService;

    @Override


    // abaixo metodo que recebe a resqust, repobse e filterchain, permite interceptar a requisição antes que chegue na controller.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // verifica se a requisição corresponde a alguma rota depois temos as rotas que vamos autorizar no if.
        // filterchain ecaminha a requisição para o proximo passo
        String uri = request.getRequestURI();

        if(uri.startsWith("/swagger-ui")
        || uri.startsWith("/v2/api-docs")
        || uri.startsWith("/v3/api-docs")
        || uri.startsWith("/swagger-resources")
        || uri.startsWith("webjars")
        || uri.startsWith("/auth/login")
        || uri.startsWith("/")
        ){
            filterChain.doFilter(request,response);

            return;

        }

        //aqui é onde vai ser enviado o TOKEN JWT
        String authHeader = request.getHeader("Authorization");

        // verifica se o cabeçho existe e no string remove(replace) o prefixo Bearer
        if(authHeader != null && authHeader.startsWith("Bearer ")){

            String token = authHeader.replace("Bearer ","");

            try{

                //variavel verifica o token, mesagem exibe o token valido como subject.

                var jwtValidador = tokenService.vericarToken(token);
                System.out.println(jwtValidador.getSubject());


            }catch (Exception e){
                // Define o status HTTP 401 (Unauthorized), retorna mensagem
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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