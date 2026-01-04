package com.skimobarber.booking.domain.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Horario laboral de un empleado para un día de la semana
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioEmpleado {
    private Long id;
    private Long empleadoId;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Domain validation
    public boolean isHorarioValido() {
        return horaInicio != null && horaFin != null && horaFin.isAfter(horaInicio);
    }

    public int getDuracionMinutos() {
        if (!isHorarioValido()) return 0;
        return (int) java.time.Duration.between(horaInicio, horaFin).toMinutes();
    }
}
