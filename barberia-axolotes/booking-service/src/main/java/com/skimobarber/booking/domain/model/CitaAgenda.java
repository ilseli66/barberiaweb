package com.skimobarber.booking.domain.model;

import java.time.LocalDateTime;

/**
 * Representa un slot de agenda para una fase de servicio de una cita.
 * Usa el concepto de tsrange de PostgreSQL para evitar solapamientos.
 */
public class CitaAgenda {
    private Long id;
    private Long citaId;
    private Long empleadoId;
    private Long servicioFaseId;
    private LocalDateTime inicio;
    private LocalDateTime fin;

    public CitaAgenda() {}

    public CitaAgenda(Long id, Long citaId, Long empleadoId, Long servicioFaseId,
                      LocalDateTime inicio, LocalDateTime fin) {
        this.id = id;
        this.citaId = citaId;
        this.empleadoId = empleadoId;
        this.servicioFaseId = servicioFaseId;
        this.inicio = inicio;
        this.fin = fin;
    }

    // Domain validation
    public boolean isPeriodoValido() {
        return inicio != null && fin != null && fin.isAfter(inicio);
    }

    public int getDuracionMinutos() {
        if (!isPeriodoValido()) return 0;
        return (int) java.time.Duration.between(inicio, fin).toMinutes();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public Long getServicioFaseId() { return servicioFaseId; }
    public void setServicioFaseId(Long servicioFaseId) { this.servicioFaseId = servicioFaseId; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
}
