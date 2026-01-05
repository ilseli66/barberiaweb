package com.skimobarber.identity.infrastructure.adapters.out.persistence.entity;

import com.skimobarber.identity.domain.model.Cliente;
import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @Column(name = "persona_id")
    private Long personaId;

    @Column(name = "puntos_fidelidad")
    private Integer puntosFidelidad = 0;

    @Column(name = "notas_alergias", columnDefinition = "TEXT")
    private String notasAlergias;

    @OneToOne
    @MapsId
    @JoinColumn(name = "persona_id")
    private PersonaEntity persona;

    public ClienteEntity() {}

    // Getters and Setters
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }

    public Integer getPuntosFidelidad() { return puntosFidelidad; }
    public void setPuntosFidelidad(Integer puntosFidelidad) { this.puntosFidelidad = puntosFidelidad; }

    public String getNotasAlergias() { return notasAlergias; }
    public void setNotasAlergias(String notasAlergias) { this.notasAlergias = notasAlergias; }

    public PersonaEntity getPersona() { return persona; }
    public void setPersona(PersonaEntity persona) { this.persona = persona; }

    // ========== Mapping Methods ==========

    public static ClienteEntity fromDomain(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setPersonaId(cliente.getPersonaId());
        entity.setPuntosFidelidad(cliente.getPuntosFidelidad());
        entity.setNotasAlergias(cliente.getNotasAlergias());
        return entity;
    }

    public Cliente toDomain() {
        return new Cliente(
            this.personaId,
            this.puntosFidelidad,
            this.notasAlergias
        );
    }
}
