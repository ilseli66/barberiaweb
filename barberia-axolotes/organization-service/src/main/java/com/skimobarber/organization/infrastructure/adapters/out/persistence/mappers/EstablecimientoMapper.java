package com.skimobarber.organization.infrastructure.adapters.out.persistence.mappers;

import com.skimobarber.organization.domain.model.Establecimiento;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.EstablecimientoEntity;

public class EstablecimientoMapper {
    public static Establecimiento toDomain(EstablecimientoEntity entity) {
        if (entity == null) return null;
        return new Establecimiento(entity.getId(), entity.getNombre());
    }

    public static EstablecimientoEntity toEntity(Establecimiento domain) {
        if (domain == null) return null;
        EstablecimientoEntity entity = new EstablecimientoEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }
}
