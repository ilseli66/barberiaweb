package com.skimobarber.booking.infrastructure.adapters.out.persistence.mapper;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.HorarioEmpleadoEntity;

import java.time.DayOfWeek;

public class HorarioEmpleadoMapper {
    public static HorarioEmpleado toDomain(HorarioEmpleadoEntity entity) {
        return new HorarioEmpleado(
            entity.getId(),
            entity.getEmpleadoId(),
            DayOfWeek.of(entity.getDiaSemana()),
            entity.getHoraInicio(),
            entity.getHoraFin()
        );
    }

    public static HorarioEmpleadoEntity toEntity(HorarioEmpleado horario) {
        HorarioEmpleadoEntity entity = new HorarioEmpleadoEntity();
        entity.setId(horario.getId());
        entity.setEmpleadoId(horario.getEmpleadoId());
        entity.setDiaSemana(horario.getDiaSemana().getValue());
        entity.setHoraInicio(horario.getHoraInicio());
        entity.setHoraFin(horario.getHoraFin());
        return entity;
    }
}
