package com.skimobarber.identity.domain.model;

import com.skimobarber.identity.domain.enums.TipoRol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long personaId;
    private String login;
    private TipoRol rol;
    private boolean activo;

    public boolean isActivo() { return activo; }

    // Domain validations
    public boolean isLoginValid() {
        return login != null && login.length() >= 5;
    }

    /**
     * Regla de negocio: Solo los clientes pueden acumular puntos de fidelidad
     */
    public boolean canAccumulatePoints() {
        return this.rol == TipoRol.CLIENTE;
    }
}
