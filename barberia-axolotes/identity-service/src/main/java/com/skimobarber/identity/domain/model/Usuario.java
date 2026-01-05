package com.skimobarber.identity.domain.model;

import com.skimobarber.identity.domain.enums.TipoRol;

public class Usuario {
    private Long personaId;
    private String login;
    private TipoRol rol;
    private boolean activo;

    public Usuario() {}

    public Usuario(Long personaId, String login, TipoRol rol, boolean activo) {
        this.personaId = personaId;
        this.login = login;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters
    public Long getPersonaId() { return personaId; }
    public String getLogin() { return login; }
    public TipoRol getRol() { return rol; }
    public boolean isActivo() { return activo; }

    // Setters
    public void setPersonaId(Long personaId) { this.personaId = personaId; }
    public void setLogin(String login) { this.login = login; }
    public void setRol(TipoRol rol) { this.rol = rol; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // Domain validations
    public boolean isLoginValid() {
        return login != null && login.length() >= 5;
    }

    /**
     * Regla de negocio: Solo los clientes pueden acumular puntos de fidelidad
     */
    public boolean canAccumulatePoints() {
        return this.rol == TipoRol.CLIENTE;
    }
}
