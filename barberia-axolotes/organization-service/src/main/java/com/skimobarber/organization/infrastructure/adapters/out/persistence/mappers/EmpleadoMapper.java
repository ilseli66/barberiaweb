package com.skimobarber.organization.infrastructure.adapters.out.persistence.mappers;

import com.skimobarber.organization.domain.model.Empleado;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.EmpleadoEntity;

public class EmpleadoMapper {
    public static Empleado toDomain(EmpleadoEntity entity) {
        if (entity == null) return null;
        return new Empleado(
            entity.getPersonaId(),
            entity.getSucursal() != null ? entity.getSucursal().getId() : null,
            entity.getEspecialidad()
        );
    }

    public static EmpleadoEntity toEntity(Empleado domain) {
        if (domain == null) return null;
        EmpleadoEntity entity = new EmpleadoEntity();
        entity.setPersonaId(domain.getPersonaId());
        entity.setEspecialidad(domain.getEspecialidad());
        entity.setSucursalId(domain.getSucursalId());
        return entity;
    }
}
