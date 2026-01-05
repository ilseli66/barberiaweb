package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

import java.util.Optional;

public interface IUsuarioRepository extends IBaseRepository<Usuario, Long> {
    /**
     * Guarda un usuario con su password encriptado.
     * El password se maneja por separado del modelo de dominio por seguridad.
     */
    Usuario saveWithPassword(Usuario usuario, String encodedPassword);
    Optional<Usuario> findByPersonaId(Long personaId);
    Optional<Usuario> findByLogin(String login);
    boolean existsByLogin(String login);
    void deleteByPersonaId(Long personaId);
}
