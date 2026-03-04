package com.tecsup.app.micro.user.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${app.security.keycloak.realm:food-ordering}")
    private String realm;

    @Value("${app.security.keycloak.base-url:http://localhost:8080}")
    private String keycloakBaseUrl;

    @Bean
    public OpenAPI openAPI() {
        String authUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/auth";
        String tokenUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        SecurityScheme oauth2 = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows().authorizationCode(
                        new OAuthFlow()
                                .authorizationUrl(authUrl)
                                .tokenUrl(tokenUrl)
                                .scopes(new Scopes()
                                        .addString("openid", "OpenID")
                                        .addString("profile", "Profile")
                                        .addString("email", "Email"))));

        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .version("1.0.0")
                        .description("User-service (perfil y direcciones) - protegido con Keycloak JWT"))
                .schemaRequirement("keycloak-oauth", oauth2)
                .addSecurityItem(new SecurityRequirement().addList("keycloak-oauth"));
    }
}