package com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper;

import com.skimobarber.catalog.domain.model.Servicio;
import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioEntity;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioFaseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ServicioMapper {
    public static Servicio toDomain(ServicioEntity entity) {
        List<ServicioFase> fases = entity.getFases() != null
            ? entity.getFases().stream().map(ServicioFaseMapper::toDomain).collect(Collectors.toList())
            : List.of();
        return new Servicio(
            entity.getId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.isActivo(),
            fases
        );
    }

    public static ServicioEntity toEntity(Servicio servicio) {
        ServicioEntity entity = new ServicioEntity();
        entity.setId(servicio.getId());
        entity.setNombre(servicio.getNombre());
        entity.setDescripcion(servicio.getDescripcion());
        entity.setActivo(servicio.isActivo());
        if (servicio.getFases() != null) {
            for (ServicioFase fase : servicio.getFases()) {
                ServicioFaseEntity faseEntity = ServicioFaseMapper.toEntity(fase);
                entity.addFase(faseEntity);
            }
        }
        return entity;
    }
}
