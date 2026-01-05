package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Usuario;

/**
 * Puerto para generación de tokens de autenticación.
 * La implementación específica (JWT, OAuth, etc.) está en infraestructura.
 */
public interface TokenProvider {
    
    /**
     * Genera un token de acceso para el usuario.
     * 
     * @param usuario El usuario autenticado
     * @return Token de acceso
     */
    String generateAccessToken(Usuario usuario);
    
    /**
     * Genera un token de refresco para el usuario.
     * 
     * @param usuario El usuario autenticado
     * @return Token de refresco
     */
    String generateRefreshToken(Usuario usuario);
    
    /**
     * Tiempo de expiración del token en segundos.
     */
    long getExpirationSeconds();
}
