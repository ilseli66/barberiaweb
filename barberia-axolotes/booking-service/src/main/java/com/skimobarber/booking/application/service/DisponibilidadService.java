package com.skimobarber.booking.application.service;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.booking.domain.ports.in.DisponibilidadUseCase;
import com.skimobarber.booking.domain.ports.out.ICitaAgendaRepository;
import com.skimobarber.booking.domain.ports.out.IHorarioEmpleadoRepository;
import com.skimobarber.common.domain.Result;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DisponibilidadService implements DisponibilidadUseCase {

    private static final int DURACION_SLOT_MINUTOS = 30;

    private final IHorarioEmpleadoRepository horarioRepository;
    private final ICitaAgendaRepository agendaRepository;

    public DisponibilidadService(IHorarioEmpleadoRepository horarioRepository,
                                  ICitaAgendaRepository agendaRepository) {
        this.horarioRepository = horarioRepository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public Result<List<SlotDisponible>> getDisponibilidadEmpleado(Long empleadoId, LocalDate fecha) {
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        
        // Obtener horario del empleado para ese día
        var horarioOpt = horarioRepository.findByEmpleadoIdAndDiaSemana(empleadoId, diaSemana);
        if (horarioOpt.isEmpty()) {
            return Result.success(List.of()); // No trabaja ese día
        }

        HorarioEmpleado horario = horarioOpt.get();
        List<SlotDisponible> slots = generarSlotsDisponibles(empleadoId, fecha, horario);
        
        return Result.success(slots);
    }

    @Override
    public Result<Boolean> isEmpleadoDisponible(Long empleadoId, LocalDateTime inicio, LocalDateTime fin) {
        LocalDate fecha = inicio.toLocalDate();
        DayOfWeek diaSemana = fecha.getDayOfWeek();

        // Verificar que el empleado trabaja ese día
        var horarioOpt = horarioRepository.findByEmpleadoIdAndDiaSemana(empleadoId, diaSemana);
        if (horarioOpt.isEmpty()) {
            return Result.success(false);
        }

        HorarioEmpleado horario = horarioOpt.get();
        
        // Verificar que el rango está dentro del horario laboral
        LocalTime horaInicio = inicio.toLocalTime();
        LocalTime horaFin = fin.toLocalTime();
        
        if (horaInicio.isBefore(horario.getHoraInicio()) || horaFin.isAfter(horario.getHoraFin())) {
            return Result.success(false);
        }

        // Verificar que no hay solapamiento con otras citas
        boolean haySolapamiento = agendaRepository.existsSolapamiento(empleadoId, inicio, fin);
        
        return Result.success(!haySolapamiento);
    }

    /**
     * Genera los slots disponibles para un empleado en una fecha específica
     */
    private List<SlotDisponible> generarSlotsDisponibles(Long empleadoId, LocalDate fecha, HorarioEmpleado horario) {
        List<SlotDisponible> slots = new ArrayList<>();
        
        LocalTime horaActual = horario.getHoraInicio();
        LocalTime horaFin = horario.getHoraFin();

        while (horaActual.plusMinutes(DURACION_SLOT_MINUTOS).compareTo(horaFin) <= 0) {
            LocalDateTime inicioSlot = LocalDateTime.of(fecha, horaActual);
            LocalDateTime finSlot = inicioSlot.plusMinutes(DURACION_SLOT_MINUTOS);

            // Verificar si el slot está libre (no hay citas)
            boolean ocupado = agendaRepository.existsSolapamiento(empleadoId, inicioSlot, finSlot);
            
            if (!ocupado) {
                slots.add(new SlotDisponible(empleadoId, inicioSlot, finSlot, DURACION_SLOT_MINUTOS));
            }

            horaActual = horaActual.plusMinutes(DURACION_SLOT_MINUTOS);
        }

        return slots;
    }
}
