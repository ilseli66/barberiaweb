package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findByPersonaId(Long personaId);
    Optional<Usuario> findByLogin(String login);
    List<Usuario> findAll();
    boolean existsByLogin(String login);
    void deleteByPersonaId(Long personaId);
}
