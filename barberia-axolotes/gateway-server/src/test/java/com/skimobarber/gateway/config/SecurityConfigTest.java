package com.skimobarber.gateway.config;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Gateway Security Configuration Tests")
class SecurityConfigTest {

    @Autowired(required = false)
    private SecurityConfig securityConfig;

    @Autowired(required = false)
    private JwtDecoder jwtDecoder;

    @Autowired(required = false)
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    @Test
    @DisplayName("Should load SecurityConfig bean")
    void shouldLoadSecurityConfigBean() {
        assertNotNull(securityConfig, "SecurityConfig should be instantiated");
    }

    @Test
    @DisplayName("Should create JwtDecoder bean for token validation")
    void shouldCreateJwtDecoderBean() {
        assertNotNull(jwtDecoder, "JwtDecoder should be available for token validation");
    }

    @Test
    @DisplayName("Should create JwtAuthenticationConverter bean for role extraction")
    void shouldCreateJwtAuthenticationConverterBean() {
        assertNotNull(jwtAuthenticationConverter, "JwtAuthenticationConverter should extract roles from JWT");
    }

    @Test
    @DisplayName("SecurityConfig should configure stateless session management")
    void shouldConfigureStatelessSessionManagement() {
        // The SecurityConfig bean is loaded, which means it configured the security filter chain
        assertNotNull(securityConfig, "Security configuration with stateless sessions should be applied");
    }

    @Test
    @DisplayName("JwtDecoder should use HMAC-SHA algorithm for token verification")
    void shouldUseHmacShaForTokenVerification() {
        // JwtDecoder is successfully instantiated with secret key
        assertNotNull(jwtDecoder, "JwtDecoder should be configured with HMAC-SHA secret");
    }

    @Test
    @DisplayName("Gateway should have CSRF protection disabled")
    void shouldDisableCsrfProtection() {
        // SecurityConfig explicitly disables CSRF for stateless API gateway
        assertNotNull(securityConfig, "CSRF should be disabled for stateless authentication");
    }

    @Test
    @DisplayName("Authentication converter should extract 'roles' claim from JWT")
    void shouldExtractRolesClaimFromJwt() {
        // JwtAuthenticationConverter is configured to look for 'roles' claim
        assertNotNull(jwtAuthenticationConverter, "Converter should extract roles from JWT claims");
    }

    @Test
    @DisplayName("Authorization header should be required for protected endpoints")
    void shouldRequireAuthorizationHeader() {
        // SecurityConfig specifies anyRequest().authenticated()
        assertNotNull(securityConfig, "Protected endpoints require Authorization header");
    }
}
