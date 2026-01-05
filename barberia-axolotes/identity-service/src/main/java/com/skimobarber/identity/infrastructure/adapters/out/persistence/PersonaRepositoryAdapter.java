package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.out.PersonaRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.PersonaEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaPersonaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonaRepositoryAdapter implements PersonaRepository {

    private final JpaPersonaRepository jpaRepository;

    public PersonaRepositoryAdapter(JpaPersonaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Persona save(Persona persona) {
        PersonaEntity entity = PersonaEntity.fromDomain(persona);
        PersonaEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Persona> findById(Long id) {
        return jpaRepository.findById(id).map(PersonaEntity::toDomain);
    }

    @Override
    public Optional<Persona> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(PersonaEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
