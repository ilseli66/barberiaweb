package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @Column(name = "persona_id")
    private Long personaId;

    @Column(nullable = false, unique = true, length = 100)
    private String login;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String rol;

    @Column(nullable = false)
    private boolean activo = true;

    @OneToOne
    @MapsId
    @JoinColumn(name = "persona_id")
    private PersonaEntity persona;

    public UsuarioEntity() {}

    // Getters and Setters
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public PersonaEntity getPersona() { return persona; }
    public void setPersona(PersonaEntity persona) { this.persona = persona; }
}
