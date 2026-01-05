package com.skimobarber.identity.domain.ports.in;

import com.skimobarber.common.domain.Result;

/**
 * Caso de uso para autenticación de usuarios.
 * Valida credenciales y devuelve tokens de acceso.
 */
public interface AuthenticateUserUseCase {
    
    Result<AuthResponse> execute(AuthCommand command);
    
    /**
     * Comando de autenticación con credenciales.
     */
    record AuthCommand(String login, String password) {}
    
    /**
     * Respuesta de autenticación exitosa.
     */
    record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        Long userId,
        String login,
        String role
    ) {}
}
