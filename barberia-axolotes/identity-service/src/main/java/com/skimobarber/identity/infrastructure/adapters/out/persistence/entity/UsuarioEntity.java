package com.skimobarber.identity.infrastructure.adapters.out.persistence.entity;

import com.skimobarber.identity.domain.enums.TipoRol;
import com.skimobarber.identity.domain.model.Usuario;
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

    // ========== Mapping Methods ==========

    /**
     * Crea una entidad desde el dominio.
     * Nota: El password debe establecerse por separado ya que el dominio no lo maneja.
     */
    public static UsuarioEntity fromDomain(Usuario usuario, String encodedPassword, PersonaEntity persona) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setPersona(persona); // @MapsId requiere que persona esté asignada
        entity.setLogin(usuario.getLogin());
        entity.setPassword(encodedPassword);
        entity.setRol(usuario.getRol().name().toLowerCase());
        entity.setActivo(usuario.isActivo());
        return entity;
    }

    /**
     * Convierte la entidad al modelo de dominio.
     * El password NO se incluye en el dominio por seguridad.
     */
    public Usuario toDomain() {
        return new Usuario(
            this.personaId,
            this.login,
            TipoRol.valueOf(this.rol.toUpperCase()),
            this.activo
        );
    }
}
