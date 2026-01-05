package com.skimobarber.identity.infrastructure.adapters.out.security;

import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.out.CredentialsVerifier;
import com.skimobarber.identity.domain.ports.out.PasswordEncoder;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.UsuarioEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaUsuarioRepository;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementación que verifica credenciales de usuario.
 * Combina el repositorio JPA con el encoder de passwords.
 */
@Component
public class CredentialsVerifierAdapter implements CredentialsVerifier {

    private final JpaUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CredentialsVerifierAdapter(JpaUsuarioRepository usuarioRepository,
                                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Usuario> verifyCredentials(String login, String rawPassword) {
        return usuarioRepository.findByLogin(login)
            .filter(entity -> passwordEncoder.matches(rawPassword, entity.getPassword()))
            .filter(UsuarioEntity::isActivo)
            .map(UsuarioEntity::toDomain);
    }
}
