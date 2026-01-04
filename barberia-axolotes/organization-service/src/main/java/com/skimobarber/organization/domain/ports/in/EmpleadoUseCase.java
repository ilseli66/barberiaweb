package com.skimobarber.organization.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.organization.domain.model.Empleado;

import java.util.List;

public interface EmpleadoUseCase {
    Result<Empleado> create(CreateEmpleadoCommand command);
    Result<Empleado> findById(Long personaId);
    Result<List<Empleado>> findBySucursalId(Long sucursalId);
    Result<List<Empleado>> findAll();
    Result<Empleado> update(Long personaId, UpdateEmpleadoCommand command);
    Result<Void> delete(Long personaId);

    record CreateEmpleadoCommand(
        Long personaId,
        Long sucursalId,
        String especialidad
    ) {}

    record UpdateEmpleadoCommand(
        Long sucursalId,
        String especialidad
    ) {}
}
