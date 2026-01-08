
package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.out.IUsuarioRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.PersonaEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.UsuarioEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaPersonaRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaUsuarioRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter extends JpaBaseAdapter<Usuario, UsuarioEntity, Long, JpaUsuarioRepository> implements IUsuarioRepository {
    private final JpaPersonaRepository personaRepository;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaRepository, JpaPersonaRepository personaRepository) {
        super(jpaRepository, UsuarioEntity::toDomain, usuario -> {
            throw new UnsupportedOperationException("Use saveWithPassword para crear usuarios nuevos con password");
        });
        this.personaRepository = personaRepository;
    }

    @Override
    public Usuario saveWithPassword(Usuario usuario, String encodedPassword) {
        PersonaEntity persona = personaRepository.findById(usuario.getPersonaId())
            .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));
        UsuarioEntity entity = UsuarioEntity.fromDomain(usuario, encodedPassword, persona);
        UsuarioEntity saved = repository.save(entity);
        return toDomain.apply(saved);
    }

    @Override
    public Usuario save(Usuario usuario) {
        // Para actualizaciones, mantenemos el password existente
        UsuarioEntity existing = repository.findById(usuario.getPersonaId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        existing.setLogin(usuario.getLogin());
        existing.setRol(usuario.getRol().name().toLowerCase());
        existing.setActivo(usuario.isActivo());
        UsuarioEntity saved = repository.save(existing);
        return toDomain.apply(saved);
    }

    @Override
    public Optional<Usuario> findByPersonaId(Long personaId) {
        return repository.findById(personaId).map(toDomain);
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return repository.findByLogin(login).map(toDomain);
    }

    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    @Override
    public void deleteByPersonaId(Long personaId) {
        repository.deleteById(personaId);
    }
}
