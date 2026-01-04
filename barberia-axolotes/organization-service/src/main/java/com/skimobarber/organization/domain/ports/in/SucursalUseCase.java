package com.skimobarber.organization.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.organization.domain.model.Sucursal;

import java.util.List;

public interface SucursalUseCase {
    Result<Sucursal> create(CreateSucursalCommand command);
    Result<Sucursal> findById(Long id);
    Result<List<Sucursal>> findByEstablecimientoId(Long establecimientoId);
    Result<List<Sucursal>> findAll();
    Result<Sucursal> update(Long id, UpdateSucursalCommand command);
    Result<Void> delete(Long id);

    record CreateSucursalCommand(
        Long establecimientoId,
        String nombre,
        Double latitud,
        Double longitud
    ) {}

    record UpdateSucursalCommand(
        String nombre,
        Double latitud,
        Double longitud
    ) {}
}
