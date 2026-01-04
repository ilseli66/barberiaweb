package com.skimobarber.booking.domain.ports.in;

import com.skimobarber.common.domain.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DisponibilidadUseCase {
    /**
     * Obtiene los slots disponibles para un empleado en un día específico
     */
    Result<List<SlotDisponible>> getDisponibilidadEmpleado(Long empleadoId, LocalDate fecha);

    /**
     * Obtiene los slots disponibles para una sucursal en un día específico
     */
    Result<List<SlotDisponible>> getDisponibilidadSucursal(Long sucursalId, LocalDate fecha);

    /**
     * Verifica si un empleado está disponible en un rango de tiempo
     */
    Result<Boolean> isEmpleadoDisponible(Long empleadoId, LocalDateTime inicio, LocalDateTime fin);

    record SlotDisponible(
        Long empleadoId,
        LocalDateTime inicio,
        LocalDateTime fin,
        int duracionMinutos
    ) {}
}
