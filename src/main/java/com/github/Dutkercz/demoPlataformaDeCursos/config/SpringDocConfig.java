package com.github.Dutkercz.demoPlataformaDeCursos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPIConfig(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("Cursos Online - Project")
                        .description("API REST do projeto plataforma de cursos.")
                        .contact(new Contact()
                                .name("Cristian T. D. Rosa")
                                .email("dutkercz@gmail.com")
                                .url("https://github.com/Dutkercz")));
    }

}
