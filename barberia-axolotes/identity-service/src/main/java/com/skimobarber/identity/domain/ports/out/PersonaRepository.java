package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Persona;

import java.util.Optional;

public interface PersonaRepository {
    Persona save(Persona persona);
    Optional<Persona> findById(Long id);
    Optional<Persona> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteById(Long id);
}
