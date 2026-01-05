package com.skimobarber.booking.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita_agenda")
public class CitaAgendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false)
    private CitaEntity cita;

    @Column(name = "empleado_id", nullable = false)
    private Long empleadoId;

    @Column(name = "servicio_fase_id")
    private Long servicioFaseId;

    @Column(nullable = false)
    private LocalDateTime inicio;

    @Column(nullable = false)
    private LocalDateTime fin;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CitaEntity getCita() { return cita; }
    public void setCita(CitaEntity cita) { this.cita = cita; }

    public Long getCitaId() { return cita != null ? cita.getId() : null; }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public Long getServicioFaseId() { return servicioFaseId; }
    public void setServicioFaseId(Long servicioFaseId) { this.servicioFaseId = servicioFaseId; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
}
