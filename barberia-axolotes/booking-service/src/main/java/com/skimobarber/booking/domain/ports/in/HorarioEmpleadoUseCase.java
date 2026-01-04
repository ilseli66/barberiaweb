package com.skimobarber.booking.domain.ports.in;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.common.domain.Result;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface HorarioEmpleadoUseCase {
    Result<HorarioEmpleado> crear(CrearHorarioCommand command);
    Result<List<HorarioEmpleado>> findByEmpleadoId(Long empleadoId);
    Result<HorarioEmpleado> update(Long id, UpdateHorarioCommand command);
    Result<Void> delete(Long id);

    record CrearHorarioCommand(
        Long empleadoId,
        DayOfWeek diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin
    ) {}

    record UpdateHorarioCommand(
        LocalTime horaInicio,
        LocalTime horaFin
    ) {}
}
