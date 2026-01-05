package com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper;

import com.skimobarber.catalog.domain.model.ServicioListaPrecio;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioListaPrecioEntity;

public class ServicioListaPrecioMapper {
    public static ServicioListaPrecio toDomain(ServicioListaPrecioEntity entity) {
        return new ServicioListaPrecio(
            entity.getServicioId(),
            entity.getListaPrecioId(),
            entity.getPrecio()
        );
    }

    public static ServicioListaPrecioEntity toEntity(ServicioListaPrecio slp) {
        ServicioListaPrecioEntity entity = new ServicioListaPrecioEntity();
        entity.setServicioId(slp.getServicioId());
        entity.setListaPrecioId(slp.getListaPrecioId());
        entity.setPrecio(slp.getPrecio());
        return entity;
    }
}
