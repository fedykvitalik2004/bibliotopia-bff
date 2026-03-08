package org.vitalii.fedyk.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OpenApi configuration used for generating documentation. */
@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Bibliotopia Storage API",
            version = "1.0.0",
            description = "API for Bibliotopia BFF",
            contact = @Contact(name = "Vitalii Fedyk", email = "fedykvitalik2004@gmail.com")))
public class OpenApiConfig {
  @Bean
  OpenAPI openAPI() {
    return new OpenAPI();
  }
}
