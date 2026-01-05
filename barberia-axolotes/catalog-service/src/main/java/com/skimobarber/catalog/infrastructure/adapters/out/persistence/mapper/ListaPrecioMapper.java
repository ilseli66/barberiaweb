package com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper;

import com.skimobarber.catalog.domain.model.ListaPrecio;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ListaPrecioEntity;

public class ListaPrecioMapper {
    public static ListaPrecio toDomain(ListaPrecioEntity entity) {
        return new ListaPrecio(
            entity.getId(),
            entity.getNombre(),
            entity.getFechaInicio(),
            entity.getFechaFin(),
            entity.isActivo()
        );
    }

    public static ListaPrecioEntity toEntity(ListaPrecio listaPrecio) {
        ListaPrecioEntity entity = new ListaPrecioEntity();
        entity.setId(listaPrecio.getId());
        entity.setNombre(listaPrecio.getNombre());
        entity.setFechaInicio(listaPrecio.getFechaInicio());
        entity.setFechaFin(listaPrecio.getFechaFin());
        entity.setActivo(listaPrecio.isActivo());
        return entity;
    }
}
