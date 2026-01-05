package com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.PersonaEntity;

import java.util.Optional;

public interface JpaPersonaRepository extends JpaRepository<PersonaEntity, Long> {
    Optional<PersonaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
