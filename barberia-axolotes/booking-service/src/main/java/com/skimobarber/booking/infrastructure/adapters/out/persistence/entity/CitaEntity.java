package com.skimobarber.booking.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cita")
public class CitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    @Column(name = "sucursal_id", nullable = false)
    private Long sucursalId;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "precio_congelado", precision = 10, scale = 2)
    private BigDecimal precioCongelado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CitaAgendaEntity> agendas = new ArrayList<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getPrecioCongelado() { return precioCongelado; }
    public void setPrecioCongelado(BigDecimal precioCongelado) { this.precioCongelado = precioCongelado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<CitaAgendaEntity> getAgendas() { return agendas; }
    public void setAgendas(List<CitaAgendaEntity> agendas) { this.agendas = agendas; }

    public void addAgenda(CitaAgendaEntity agenda) {
        agendas.add(agenda);
        agenda.setCita(this);
    }

    public void removeAgenda(CitaAgendaEntity agenda) {
        agendas.remove(agenda);
        agenda.setCita(null);
    }
}
