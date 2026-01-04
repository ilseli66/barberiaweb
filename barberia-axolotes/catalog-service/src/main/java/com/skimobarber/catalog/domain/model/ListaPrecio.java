package com.skimobarber.catalog.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ListaPrecio {
    private Long id;
    private String nombre;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean activo;

    public ListaPrecio() {}

    public ListaPrecio(Long id, String nombre, LocalDateTime fechaInicio,
                       LocalDateTime fechaFin, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activo = activo;
    }

    // Domain validation
    public boolean isFechasValid() {
        if (fechaInicio == null) return false;
        if (fechaFin != null && fechaFin.isBefore(fechaInicio)) return false;
        return true;
    }

    public boolean isVigente() {
        LocalDateTime now = LocalDateTime.now();
        if (!activo) return false;
        if (now.isBefore(fechaInicio)) return false;
        if (fechaFin != null && now.isAfter(fechaFin)) return false;
        return true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
