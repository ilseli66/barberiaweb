package com.skimobarber.catalog.domain.ports.in;

import com.skimobarber.catalog.domain.model.Servicio;
import com.skimobarber.common.domain.Result;

import java.util.List;

public interface ServicioUseCase {
    Result<Servicio> create(CreateServicioCommand command);
    Result<Servicio> findById(Long id);
    Result<List<Servicio>> findAll();
    Result<List<Servicio>> findActivos();
    Result<Servicio> update(Long id, UpdateServicioCommand command);
    Result<Servicio> activar(Long id);
    Result<Servicio> desactivar(Long id);
    Result<Void> delete(Long id);

    record CreateServicioCommand(
        String nombre,
        String descripcion
    ) {}

    record UpdateServicioCommand(
        String nombre,
        String descripcion
    ) {}
}
