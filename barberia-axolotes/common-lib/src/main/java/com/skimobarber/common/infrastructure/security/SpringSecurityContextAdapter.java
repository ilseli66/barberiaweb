package com.skimobarber.common.infrastructure.security;

import com.skimobarber.common.application.ports.SecurityContextPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del puerto de seguridad que traduce el contexto de Spring Security
 * al puerto definido en la capa de aplicación.
 * 
 * Este adaptador extrae la información del JWT cargado por Spring Security
 * y la expone de forma agnóstica al framework.
 */
@Component
public class SpringSecurityContextAdapter implements SecurityContextPort {

    @Override
    public Optional<Long> getCurrentUserId() {
        return getJwt().map(jwt -> jwt.getClaim("userId"));
    }

    @Override
    public Optional<String> getCurrentUserLogin() {
        return getJwt().map(Jwt::getSubject);
    }

    @Override
    public List<String> getCurrentUserRoles() {
        return getJwt()
            .map(jwt -> {
                List<String> roles = jwt.getClaim("roles");
                return roles != null ? roles : Collections.<String>emptyList();
            })
            .orElse(Collections.emptyList());
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null 
            && authentication.isAuthenticated() 
            && authentication.getPrincipal() instanceof Jwt;
    }

    private Optional<Jwt> getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return Optional.empty();
        }
        return Optional.of(jwt);
    }
}
