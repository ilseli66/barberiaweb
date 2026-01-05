package com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio_fase")
public class ServicioFaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    @Column(nullable = false)
    private Integer orden;

    @Column(name = "nombre_fase", nullable = false, length = 100)
    private String nombreFase;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(name = "requiere_empleado", nullable = false)
    private boolean requiereEmpleado;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ServicioEntity getServicio() { return servicio; }
    public void setServicio(ServicioEntity servicio) { this.servicio = servicio; }

    public Long getServicioId() { return servicio != null ? servicio.getId() : null; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public String getNombreFase() { return nombreFase; }
    public void setNombreFase(String nombreFase) { this.nombreFase = nombreFase; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public boolean isRequiereEmpleado() { return requiereEmpleado; }
    public void setRequiereEmpleado(boolean requiereEmpleado) { this.requiereEmpleado = requiereEmpleado; }
}
