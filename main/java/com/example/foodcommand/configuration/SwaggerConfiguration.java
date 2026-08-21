package com.example.foodcommand.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpemApi(){

        return new OpenAPI().info(new Info()
                .title("FoodCommand")
                .version("1.0.0")
                .description("Api FoodCommand para aula da 4 fase"));
    }
}
