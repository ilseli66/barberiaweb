package com.skimobarber.identity.infrastructure.adapters.out.persistence.entity;

import com.skimobarber.identity.domain.model.Persona;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "persona")
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genero_id")
    private Long generoId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "primer_apellido", nullable = false, length = 100)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 100)
    private String segundoApellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150, unique = true)
    private String email;

    public PersonaEntity() {}

    public static PersonaEntity fromDomain(Persona persona) {
        PersonaEntity entity = new PersonaEntity();
        entity.setId(persona.getId());
        entity.setGeneroId(persona.getGeneroId());
        entity.setNombre(persona.getNombre());
        entity.setPrimerApellido(persona.getPrimerApellido());
        entity.setSegundoApellido(persona.getSegundoApellido());
        entity.setFechaNacimiento(persona.getFechaNacimiento());
        entity.setTelefono(persona.getTelefono());
        entity.setEmail(persona.getEmail());
        return entity;
    }

    public Persona toDomain() {
        return new Persona(
            this.id,
            this.generoId,
            this.nombre,
            this.primerApellido,
            this.segundoApellido,
            this.fechaNacimiento,
            this.telefono,
            this.email
        );
    }
}
