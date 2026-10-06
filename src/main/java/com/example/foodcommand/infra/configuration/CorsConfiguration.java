package com.example.foodcommand.infra.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// Informa esta classe contém configurações
// Declara a classe CorsConfiguration e implementa a interface WebMvcConfigurer
// Override:Indica que o método abaixo sobrescreve um método

@Configuration

public class CorsConfiguration implements WebMvcConfigurer {

    @Override


    public void addCorsMappings(CorsRegistry registry) {
        // Aplica as regras de CORS a todos os caminhos disponibilizados pelo backend
        // executa em localhost na porta 3000 sejam aceitas.
        // Define os métodos HTTP permitidos para essas requisições.

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","PATCH","HEAD");


    }
}
