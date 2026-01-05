package com.skimobarber.identity.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private Long personaId;
    private Integer puntosFidelidad;
    private String notasAlergias;

    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntosFidelidad += puntos;
        }
    }

    public boolean canjearPuntos(int puntos) {
        if (puntos > 0 && this.puntosFidelidad >= puntos) {
            this.puntosFidelidad -= puntos;
            return true;
        }
        return false;
    }
}
