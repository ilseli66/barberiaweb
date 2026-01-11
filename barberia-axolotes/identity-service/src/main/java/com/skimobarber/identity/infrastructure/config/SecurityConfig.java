package com.skimobarber.identity.infrastructure.config;

import com.skimobarber.identity.infrastructure.adapters.out.security.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Swagger UI - acceso público
                .requestMatchers(
                    "**/v3/api-docs/**", 
                    "/v3/api-docs/**", 
                    "/api/identity/v3/api-docs",
                    "/swagger-ui/**"
                ).permitAll()
                // Endpoints públicos (login, registro)
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/clientes").permitAll() // Registro de clientes
                .requestMatchers("/actuator/**").permitAll()
                // Géneros - lectura pública, escritura autenticada
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/generos/**").permitAll()
                // Endpoints protegidos
                .requestMatchers("/api/usuarios/**").authenticated()
                .requestMatchers("/api/clientes/**").authenticated()
                .requestMatchers("/api/personas/**").authenticated()
                .requestMatchers("/api/generos/**").authenticated()
                .anyRequest().authenticated()
            )
            // Configurar como Resource Server con JWT
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder())
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    /**
     * Decodificador de JWT usando la clave secreta compartida.
     * Usa HS384 para coincidir con el algoritmo usado por jjwt al firmar.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(jwtProvider.getSecretKey())
                .macAlgorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS384)
                .build();
    }

    /**
     * Converter para extraer roles del JWT y convertirlos a authorities.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
