package com.skimobarber.identity.domain.model;

public class Cliente {
    private Long personaId;
    private Integer puntosFidelidad;
    private String notasAlergias;

    public Cliente() {}

    public Cliente(Long personaId, Integer puntosFidelidad, String notasAlergias) {
        this.personaId = personaId;
        this.puntosFidelidad = puntosFidelidad != null ? puntosFidelidad : 0;
        this.notasAlergias = notasAlergias;
    }

    // Domain behavior
    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntosFidelidad += puntos;
        }
    }

    public boolean canjearPuntos(int puntos) {
        if (puntos > 0 && this.puntosFidelidad >= puntos) {
            this.puntosFidelidad -= puntos;
            return true;
        }
        return false;
    }

    // Getters and Setters
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }

    public Integer getPuntosFidelidad() { return puntosFidelidad; }
    public void setPuntosFidelidad(Integer puntosFidelidad) { this.puntosFidelidad = puntosFidelidad; }

    public String getNotasAlergias() { return notasAlergias; }
    public void setNotasAlergias(String notasAlergias) { this.notasAlergias = notasAlergias; }
}
