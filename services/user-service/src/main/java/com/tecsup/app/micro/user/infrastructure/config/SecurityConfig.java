package com.tecsup.app.micro.user.infrastructure.config;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  /**
   * Chain 1 (Order 1): Swagger UI y OpenAPI docs PÚBLICOS.
   * La interfaz gráfica carga directo. La autenticación real se hace
   * desde el botón "Authorize" de Swagger (flujo PKCE en el navegador).
   */
  @Bean
  @Order(1)
  public SecurityFilterChain swaggerChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher(
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**")
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll());

    return http.build();
  }

  /**
   * Chain 2 (Order 2): API protegida como Resource Server (JWT).
   * Todo lo que no sea Swagger (tus endpoints /api/v1/**) cae aquí
   * y requiere un Bearer Token válido.
   */
  @Bean
  @Order(2)
  public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        //.cors(Customizer.withDefaults())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  /**
   * CORS para dev (Angular/React localhost:4200, 3000, etc).
   */
  // @Bean
  // public CorsConfigurationSource corsConfigurationSource() {
  //   CorsConfiguration config = new CorsConfiguration();
  //   config.setAllowedOrigins(List.of("http://localhost:4200"));
  //   config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
  //   config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
  //   config.setAllowCredentials(true);

  //   UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //   source.registerCorsConfiguration("/**", config);
  //   return source;
  // }

  /**
   * Mapea roles de Keycloak (realm_access.roles) -> authorities Spring.
   */
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(keycloakRealmRolesConverter());
    return converter;
  }

  private Converter<Jwt, Collection<GrantedAuthority>> keycloakRealmRolesConverter() {
    return jwt -> {
      Map<String, Object> realmAccess = jwt.getClaim("realm_access");
      if (realmAccess == null)
        return List.of();

      Object rolesObj = realmAccess.get("roles");
      if (!(rolesObj instanceof Collection<?> roles))
        return List.of();

      return roles.stream()
          .filter(Objects::nonNull)
          .map(Object::toString)
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toUnmodifiableList());
    };
  }
}