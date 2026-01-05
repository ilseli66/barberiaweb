package com.skimobarber.identity.infrastructure.adapters.out.persistence;

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
    public Usuario saveWithPassword(Usuario usuario, String encodedPassword) {
        PersonaEntity persona = personaRepository.findById(usuario.getPersonaId())
            .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        UsuarioEntity entity = UsuarioEntity.fromDomain(usuario, encodedPassword);
        entity.setPersona(persona);
        UsuarioEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Usuario save(Usuario usuario) {
        // Para actualizaciones, mantenemos el password existente
        UsuarioEntity existing = jpaRepository.findById(usuario.getPersonaId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        existing.setLogin(usuario.getLogin());
        existing.setRol(usuario.getRol().name().toLowerCase());
        existing.setActivo(usuario.isActivo());
        
        UsuarioEntity saved = jpaRepository.save(existing);
        return saved.toDomain();
    }

    @Override
    public Optional<Usuario> findByPersonaId(Long personaId) {
        return jpaRepository.findById(personaId).map(UsuarioEntity::toDomain);
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return jpaRepository.findByLogin(login).map(UsuarioEntity::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream()
            .map(UsuarioEntity::toDomain)
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
}
