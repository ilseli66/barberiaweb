package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import com.skimobarber.identity.domain.model.TipoRol;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.out.UsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpaRepository;
    private final JpaPersonaRepository personaRepository;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaRepository,
                                     JpaPersonaRepository personaRepository) {
        this.jpaRepository = jpaRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        PersonaEntity persona = personaRepository.findById(usuario.getPersonaId())
            .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        UsuarioEntity entity = toEntity(usuario);
        entity.setPersona(persona);
        UsuarioEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Usuario> findByPersonaId(Long personaId) {
        return jpaRepository.findById(personaId).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return jpaRepository.findByLogin(login).map(this::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public boolean existsByLogin(String login) {
        return jpaRepository.existsByLogin(login);
    }

    @Override
    public void deleteByPersonaId(Long personaId) {
        jpaRepository.deleteById(personaId);
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setPersonaId(usuario.getPersonaId());
        entity.setLogin(usuario.getLogin());
        entity.setPassword(usuario.getPassword());
        entity.setRol(usuario.getRol().name().toLowerCase());
        entity.setActivo(usuario.isActivo());
        return entity;
    }

    private Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
            entity.getPersonaId(),
            entity.getLogin(),
            entity.getPassword(),
            TipoRol.valueOf(entity.getRol().toUpperCase()),
            entity.isActivo()
        );
    }
}
