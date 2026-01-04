package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.out.PersonaRepository;
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
        PersonaEntity entity = toEntity(persona);
        PersonaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Persona> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Persona> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    private PersonaEntity toEntity(Persona persona) {
        PersonaEntity entity = new PersonaEntity();
        entity.setId(persona.getId());
        entity.setGeneroId(persona.getGeneroId());
        entity.setNombre(persona.getNombre());
        entity.setPrimerApellido(persona.getPrimerApellido());
        entity.setSegundoApellido(persona.getSegundoApellido());
        entity.setFechaNacimiento(persona.getFechaNacimiento());
        entity.setTelefono(persona.getTelefono());
        entity.setEmail(persona.getEmail());
        return entity;
    }

    private Persona toDomain(PersonaEntity entity) {
        return new Persona(
            entity.getId(),
            entity.getGeneroId(),
            entity.getNombre(),
            entity.getPrimerApellido(),
            entity.getSegundoApellido(),
            entity.getFechaNacimiento(),
            entity.getTelefono(),
            entity.getEmail()
        );
    }
}
