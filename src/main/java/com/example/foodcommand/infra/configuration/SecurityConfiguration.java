package com.example.foodcommand.infra.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login",
                                "/usuarios/empresadono",
                                "/usuarios/usuarioadmin",
                                "/tiporestaurante/criar",
                                "/error",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // Aqui faz: criar, alterar e excluir só para usuário de empresa (EMPRESA_DONO ou USER).
                        // O Admin do Sistema (ROLE_ADMIN) não está na lista, então leva 403 em qualquer escrita.
                        // Consultas (GET) não entram aqui, então o Admin do Sistema consegue ver tudo.
                        .requestMatchers(HttpMethod.POST).hasAnyRole("EMPRESA_DONO", "USER")      // NOVO
                        .requestMatchers(HttpMethod.PUT).hasAnyRole("EMPRESA_DONO", "USER")       // NOVO
                        .requestMatchers(HttpMethod.PATCH).hasAnyRole("EMPRESA_DONO", "USER")     // NOVO
                        .requestMatchers(HttpMethod.DELETE).hasAnyRole("EMPRESA_DONO", "USER")    // NOVO
                        .anyRequest().authenticated()      // no TESTE CHAVE: troque por .permitAll()
                )
                // Alterado: tratamento de quem tenta acessar rota protegida SEM token.
                // Aqui faz: quando a requisição chega sem token numa rota que exige login,
                // o Spring Security chama este bloco, que devolve 401 (não autorizado) com a mensagem.
                // Antes isso era feito por 5 linhas dentro do JwtFilter. Foi movido para cá para que
                // a decisão de bloquear fique num lugar só: a configuração de segurança.
                // Sem este bloco, o Spring responderia 403 por padrão, e o interceptor do React espera 401.
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().println("Token não informado");
                        })
                        // Adicionado - Aqui faz: quando a pessoa está logada mas não tem permissão (o Admin do Sistema tentando
                        // criar/alterar/excluir), devolve 403 com uma mensagem clara em vez de uma resposta vazia.
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().println("Acesso negado: o usuário Admin do Sistema só pode consultar.");
                        }))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}