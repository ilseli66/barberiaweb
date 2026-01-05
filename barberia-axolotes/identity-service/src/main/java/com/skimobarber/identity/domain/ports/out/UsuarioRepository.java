package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    /**
     * Guarda un usuario con su password encriptado.
     * El password se maneja por separado del modelo de dominio por seguridad.
     */
    Usuario saveWithPassword(Usuario usuario, String encodedPassword);
    
    /**
     * Actualiza un usuario existente (sin modificar password).
     */
    Usuario save(Usuario usuario);
    
    Optional<Usuario> findByPersonaId(Long personaId);
    Optional<Usuario> findByLogin(String login);
    List<Usuario> findAll();
    boolean existsByLogin(String login);
    void deleteByPersonaId(Long personaId);
}
