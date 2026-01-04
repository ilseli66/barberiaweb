package com.skimobarber.catalog.domain.model;

public class Servicio {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;

    public Servicio() {}

    public Servicio(Long id, String nombre, String descripcion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Domain validation
    public boolean isNombreValid() {
        return nombre != null && !nombre.isBlank() && nombre.length() <= 100;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
