package com.skimobarber.booking.infrastructure.adapters.out.persistence.mapper;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.booking.domain.model.EstadoCita;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.CitaEntity;

public class CitaMapper {
    public static Cita toDomain(CitaEntity entity) {
        return new Cita(
            entity.getId(),
            entity.getClienteId(),
            entity.getServicioId(),
            entity.getSucursalId(),
            EstadoCita.valueOf(entity.getEstado().toUpperCase()),
            entity.getPrecioCongelado(),
            entity.getFechaCreacion()
        );
    }

    public static CitaEntity toEntity(Cita cita) {
        CitaEntity entity = new CitaEntity();
        entity.setId(cita.getId());
        entity.setClienteId(cita.getClienteId());
        entity.setServicioId(cita.getServicioId());
        entity.setSucursalId(cita.getSucursalId());
        entity.setEstado(cita.getEstado().name().toLowerCase());
        entity.setPrecioCongelado(cita.getPrecioCongelado());
        entity.setFechaCreacion(cita.getFechaCreacion());
        return entity;
    }
}
