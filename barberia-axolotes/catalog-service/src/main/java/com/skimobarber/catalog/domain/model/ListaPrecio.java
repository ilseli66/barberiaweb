package com.skimobarber.catalog.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPrecio {
    private Long id;
    private String nombre;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean activo;

    // Domain validation
    public boolean isFechasValid() {
        if (fechaInicio == null) return false;
        if (fechaFin != null && fechaFin.isBefore(fechaInicio)) return false;
        return true;
    }

    public boolean isVigente() {
        LocalDateTime now = LocalDateTime.now();
        if (!activo) return false;
        if (now.isBefore(fechaInicio)) return false;
        if (fechaFin != null && now.isAfter(fechaFin)) return false;
        return true;
    }
}