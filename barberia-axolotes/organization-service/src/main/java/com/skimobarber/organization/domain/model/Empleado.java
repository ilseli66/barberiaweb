package com.skimobarber.organization.domain.model;

public class Empleado {
    private Long personaId;
    private Long sucursalId;
    private String especialidad;

    public Empleado() {}

    public Empleado(Long personaId, Long sucursalId, String especialidad) {
        this.personaId = personaId;
        this.sucursalId = sucursalId;
        this.especialidad = especialidad;
    }

    // Getters and Setters
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
