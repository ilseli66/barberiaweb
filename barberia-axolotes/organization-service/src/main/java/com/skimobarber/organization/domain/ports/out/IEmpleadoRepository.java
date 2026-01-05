package com.skimobarber.organization.domain.ports.out;

import com.skimobarber.common.domain.ports.out.IBaseRepository;
import com.skimobarber.organization.domain.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface IEmpleadoRepository extends IBaseRepository<Empleado, Long> {
    Optional<Empleado> findByPersonaId(Long personaId);
    List<Empleado> findBySucursalId(Long sucursalId);
    void deleteByPersonaId(Long personaId);
    boolean existsByPersonaId(Long personaId);
}
