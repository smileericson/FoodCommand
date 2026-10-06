package com.example.foodcommand.infra.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// 1 - identifica o spring que tem classificação gerenciadas pela aplicação
//2 indica o spring que o retorno é registrado como Bean no contexto de aplicação

@Configuration

public class SwaggerConfiguration {

    @Bean
    // abaixo : Cria e configura o objeto principal da documentação da API.
    // dentro do return new 1- adiciona o requisito de segurança
    // 2- registra os compornentes incluindo o schema
    // no new security, 1 -cria a configuração do schema, 2 defino o tipo, 3 defini o schema, 4 informa o formato do token.
    public OpenAPI customOpemApi(){


        return new OpenAPI()

                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        ))

                // 1 - define as informações gerais, 2 - define titulo, 3-  defino a versão, 4 - defini a descrição apresentada.
                .info(new Info()
                .title("FoodCommand")
                .version("1.0.0")
                .description("Api FoodCommand para aula da 4 fase"));
    }
}
