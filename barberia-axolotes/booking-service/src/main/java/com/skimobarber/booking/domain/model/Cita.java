package com.skimobarber.booking.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Cita {
    private Long id;
    private Long clienteId;
    private Long servicioId;
    private Long sucursalId;
    private EstadoCita estado;
    private BigDecimal precioCongelado; // Precio al momento de la cita
    private LocalDateTime fechaCreacion;

    public Cita() {}

    public Cita(Long id, Long clienteId, Long servicioId, Long sucursalId,
                EstadoCita estado, BigDecimal precioCongelado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.clienteId = clienteId;
        this.servicioId = servicioId;
        this.sucursalId = sucursalId;
        this.estado = estado;
        this.precioCongelado = precioCongelado;
        this.fechaCreacion = fechaCreacion;
    }

    // Domain behavior
    public boolean puedeSerCancelada() {
        return estado == EstadoCita.PROGRAMADA;
    }

    public boolean puedeIniciar() {
        return estado == EstadoCita.PROGRAMADA;
    }

    public boolean puedeCompletar() {
        return estado == EstadoCita.EN_CURSO;
    }

    public void cancelar() {
        if (!puedeSerCancelada()) {
            throw new IllegalStateException("La cita no puede ser cancelada en estado: " + estado);
        }
        this.estado = EstadoCita.CANCELADA;
    }

    public void iniciar() {
        if (!puedeIniciar()) {
            throw new IllegalStateException("La cita no puede iniciarse en estado: " + estado);
        }
        this.estado = EstadoCita.EN_CURSO;
    }

    public void completar() {
        if (!puedeCompletar()) {
            throw new IllegalStateException("La cita no puede completarse en estado: " + estado);
        }
        this.estado = EstadoCita.COMPLETADA;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public BigDecimal getPrecioCongelado() { return precioCongelado; }
    public void setPrecioCongelado(BigDecimal precioCongelado) { this.precioCongelado = precioCongelado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
