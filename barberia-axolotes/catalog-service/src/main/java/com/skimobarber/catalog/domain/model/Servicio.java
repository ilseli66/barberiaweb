package com.skimobarber.catalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;

    // Domain validation
    public boolean isNombreValid() {
        return nombre != null && !nombre.isBlank() && nombre.length() <= 100;
    }
}
