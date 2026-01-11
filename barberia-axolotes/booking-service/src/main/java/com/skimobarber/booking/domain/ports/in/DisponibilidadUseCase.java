package com.skimobarber.booking.domain.ports.in;

import com.skimobarber.common.domain.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DisponibilidadUseCase {
    Result<List<SlotDisponible>> getDisponibilidadEmpleado(Long empleadoId, LocalDate fecha);
    Result<Boolean> isEmpleadoDisponible(Long empleadoId, LocalDateTime inicio, LocalDateTime fin);

    record SlotDisponible(
        Long empleadoId,
        LocalDateTime inicio,
        LocalDateTime fin,
        int duracionMinutos
    ) {}
}
