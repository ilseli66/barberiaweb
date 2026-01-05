package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Usuario;

import java.util.Optional;

/**
 * Puerto para verificación de credenciales de usuario.
 * El password nunca llega al dominio, solo se valida en infraestructura.
 */
public interface CredentialsVerifier {
    
    /**
     * Verifica las credenciales de un usuario.
     * 
     * @param login El login del usuario
     * @param rawPassword El password en texto plano
     * @return Optional con el Usuario si las credenciales son válidas, empty si no.
     */
    Optional<Usuario> verifyCredentials(String login, String rawPassword);
}
