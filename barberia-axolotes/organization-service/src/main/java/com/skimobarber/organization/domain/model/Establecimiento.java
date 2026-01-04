package com.skimobarber.organization.domain.model;

public class Establecimiento {
    private Long id;
    private String nombre;

    public Establecimiento() {}

    public Establecimiento(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
