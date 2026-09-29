package com.example.foodcommand.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// Informa ao Spring que esta classe contém configurações
// que devem ser reconhecidas durante a inicialização da aplicação.
public class CorsConfiguration implements WebMvcConfigurer {
// Declara a classe CorsConfiguration e implementa a interface
// WebMvcConfigurer para personalizar as configurações do Spring MVC.

    @Override
    // Indica que o método abaixo sobrescreve um método
    // definido na interface WebMvcConfigurer.
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Aplica as regras de CORS a todos os caminhos
                // disponibilizados pela aplicação backend.
                .allowedOrigins("http://localhost:3000")
                // Permite que requisições originadas do frontend
                // executado em localhost na porta 3000 sejam aceitas.
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","PATCH","HEAD");
                // Define os métodos HTTP permitidos para essas requisições.
                // Inclui consulta, cadastro, atualização e exclusão,
                // além dos métodos de suporte e consulta de cabeçalhos.
    }
}
