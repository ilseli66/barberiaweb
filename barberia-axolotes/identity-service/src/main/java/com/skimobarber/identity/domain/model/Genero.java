package com.skimobarber.identity.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genero {
    private Long id;
    private String nombre;
    private boolean activo;
}
