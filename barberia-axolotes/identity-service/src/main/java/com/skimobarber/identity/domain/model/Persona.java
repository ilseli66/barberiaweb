package com.skimobarber.identity.domain.model;

import java.time.LocalDate;

public class Persona {
    private Long id;
    private Long generoId;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;

    public Persona() {}

    public Persona(Long id, Long generoId, String nombre, String primerApellido, 
                   String segundoApellido, LocalDate fechaNacimiento, String telefono, String email) {
        this.id = id;
        this.generoId = generoId;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters
    public Long getId() { return id; }
    public Long getGeneroId() { return generoId; }
    public String getNombre() { return nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setGeneroId(Long generoId) { this.generoId = generoId; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }

    // Domain validations
    public boolean isEmailValid() {
        return email != null && email.contains("@") && email.length() >= 5;
    }

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder(nombre);
        sb.append(" ").append(primerApellido);
        if (segundoApellido != null && !segundoApellido.isBlank()) {
            sb.append(" ").append(segundoApellido);
        }
        return sb.toString();
    }
}
