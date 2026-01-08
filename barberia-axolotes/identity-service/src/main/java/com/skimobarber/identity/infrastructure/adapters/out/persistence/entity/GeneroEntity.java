package com.skimobarber.identity.infrastructure.adapters.out.persistence.entity;

import com.skimobarber.identity.domain.model.Genero;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "genero")
public class GeneroEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo")
    private boolean activo;

    public GeneroEntity(){}

    public static GeneroEntity fromDomain(com.skimobarber.identity.domain.model.Genero genero) {
        GeneroEntity entity = new GeneroEntity();
        entity.setId(genero.getId());
        entity.setNombre(genero.getNombre());
        entity.setActivo(genero.isActivo());
        return entity;
    }

    public Genero toDomain() {
        Genero genero = new Genero();
        genero.setId(this.getId());
        genero.setNombre(this.getNombre());
        genero.setActivo(this.isActivo());
        return genero;
    }
}
