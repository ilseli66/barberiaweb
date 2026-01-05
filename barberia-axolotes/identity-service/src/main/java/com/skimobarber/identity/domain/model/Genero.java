package com.skimobarber.identity.domain.model;

public class Genero {
    private Long id;
    private String nombre;
    private boolean activo;

    public Genero() {}

    public Genero(Long id, String nombre, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isActivo() { return activo; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
