package com.skimobarber.organization.domain.model;

public class Sucursal {
    private Long id;
    private Long establecimientoId;
    private String nombre;
    private Double latitud;
    private Double longitud;

    public Sucursal() {}

    public Sucursal(Long id, Long establecimientoId, String nombre, Double latitud, Double longitud) {
        this.id = id;
        this.establecimientoId = establecimientoId;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Domain validation
    public boolean isNombreValid() {
        return nombre != null && !nombre.isBlank() && nombre.length() <= 100;
    }

    public boolean hasUbicacion() {
        return latitud != null && longitud != null;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEstablecimientoId() { return establecimientoId; }
    public void setEstablecimientoId(Long establecimientoId) { this.establecimientoId = establecimientoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
}
