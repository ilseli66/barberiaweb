package com.skimobarber.booking.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un slot de agenda para una fase de servicio de una cita.
 * Usa el concepto de tsrange de PostgreSQL para evitar solapamientos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaAgenda {
    private Long id;
    private Long citaId;
    private Long empleadoId;
    private Long servicioFaseId;
    private LocalDateTime inicio;
    private LocalDateTime fin;

    // Domain validation
    public boolean isPeriodoValido() {
        return inicio != null && fin != null && fin.isAfter(inicio);
    }

    public int getDuracionMinutos() {
        if (!isPeriodoValido()) return 0;
        return (int) java.time.Duration.between(inicio, fin).toMinutes();
    }
}
