package com.skimobarber.booking.domain.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Horario laboral de un empleado para un día de la semana
 */
public class HorarioEmpleado {
    private Long id;
    private Long empleadoId;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public HorarioEmpleado() {}

    public HorarioEmpleado(Long id, Long empleadoId, DayOfWeek diaSemana,
                           LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Domain validation
    public boolean isHorarioValido() {
        return horaInicio != null && horaFin != null && horaFin.isAfter(horaInicio);
    }

    public int getDuracionMinutos() {
        if (!isHorarioValido()) return 0;
        return (int) java.time.Duration.between(horaInicio, horaFin).toMinutes();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public DayOfWeek getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DayOfWeek diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}
