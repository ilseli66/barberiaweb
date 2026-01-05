package com.skimobarber.organization.infrastructure.adapters.out.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.EmpleadoEntity;

public interface IEmpleadoJpaRepository extends JpaRepository<EmpleadoEntity, Long> {
    Optional<EmpleadoEntity> findByPersonaId(Long personaId);
    List<EmpleadoEntity> findBySucursalId(Long sucursalId);
    void deleteByPersonaId(Long personaId);
    boolean existsByPersonaId(Long personaId);
}
