package com.skimobarber.identity.domain.model;

public class Usuario {
    private Long personaId;
    private String login;
    private String password;
    private TipoRol rol;
    private boolean activo;

    public Usuario() {}

    public Usuario(Long personaId, String login, String password, TipoRol rol, boolean activo) {
        this.personaId = personaId;
        this.login = login;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
    }

    // Domain validations
    public boolean isLoginValid() {
        return login != null && login.length() >= 5;
    }

    public boolean isPasswordSecure() {
        return password != null && password.length() >= 8;
    }

    // Getters and Setters
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public TipoRol getRol() { return rol; }
    public void setRol(TipoRol rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
