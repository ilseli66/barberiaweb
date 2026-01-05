package com.skimobarber.booking.infrastructure.adapters.out.persistence.mapper;

import com.skimobarber.booking.domain.model.CitaAgenda;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.CitaAgendaEntity;

public class CitaAgendaMapper {
    public static CitaAgenda toDomain(CitaAgendaEntity entity) {
        return new CitaAgenda(
            entity.getId(),
            entity.getCitaId(),
            entity.getEmpleadoId(),
            entity.getServicioFaseId(),
            entity.getInicio(),
            entity.getFin()
        );
    }

    public static CitaAgendaEntity toEntity(CitaAgenda agenda) {
        CitaAgendaEntity entity = new CitaAgendaEntity();
        entity.setId(agenda.getId());
        entity.setEmpleadoId(agenda.getEmpleadoId());
        entity.setServicioFaseId(agenda.getServicioFaseId());
        entity.setInicio(agenda.getInicio());
        entity.setFin(agenda.getFin());
        return entity;
    }
}
