package com.example.foodcommand.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Indica ao Spring que esta classe possui
// configurações que devem ser gerenciadas pela aplicação.
public class SwaggerConfiguration {

    @Bean
    // Indica ao Spring que o retorno deste método
    // deve ser registrado como um Bean no contexto da aplicação.
    public OpenAPI customOpemApi(){
        // Cria e configura o objeto principal da documentação da API.

        return new OpenAPI()

                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                // Adiciona um requisito de segurança à documentação,
                .components(new Components().addSecuritySchemes("bearerAuth",
                        // Registra os componentes utilizados na documentação,
                        // incluindo o esquema de autenticação
                        new SecurityScheme()
                                // Cria a configuração do esquema de segurança.
                                .type(SecurityScheme.Type.HTTP)
                                // Define o tipo de autenticação como HTTP.
                                .scheme("bearer")
                                // Define o esquema de autenticação como Bearer.
                                .bearerFormat("JWT")
                                // Informa que o formato do token utilizado
                        ))
                .info(new Info()
                        // Define as informações gerais da documentação da API.
                .title("FoodCommand")
                        // Define o título apresentado na documentação.
                .version("1.0.0")
                        // Define a versão da API documentada.
                .description("Api FoodCommand para aula da 4 fase"));
                        // Define a descrição apresentada no Swagger.
    }
}
