package com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper;

import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioFaseEntity;

public class ServicioFaseMapper {
    public static ServicioFase toDomain(ServicioFaseEntity entity) {
        return new ServicioFase(
            entity.getId(),
            entity.getServicioId(),
            entity.getOrden(),
            entity.getNombreFase(),
            entity.getDuracionMinutos(),
            entity.isRequiereEmpleado()
        );
    }

    public static ServicioFaseEntity toEntity(ServicioFase fase) {
        ServicioFaseEntity entity = new ServicioFaseEntity();
        entity.setId(fase.getId());
        entity.setOrden(fase.getOrden());
        entity.setNombreFase(fase.getNombreFase());
        entity.setDuracionMinutos(fase.getDuracionMinutos());
        entity.setRequiereEmpleado(fase.isRequiereEmpleado());
        return entity;
    }
}
