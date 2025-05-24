package com.example.aps.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("APS Microservice API")
                    .version("1.0")
                    .description("REST API for APS platform"));
    }
}
