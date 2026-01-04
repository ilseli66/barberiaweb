package com.skimobarber.organization.domain.ports.out;

import com.skimobarber.organization.domain.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository {
    Empleado save(Empleado empleado);
    Optional<Empleado> findByPersonaId(Long personaId);
    List<Empleado> findBySucursalId(Long sucursalId);
    List<Empleado> findAll();
    void deleteByPersonaId(Long personaId);
    boolean existsByPersonaId(Long personaId);
}
