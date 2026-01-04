package com.skimobarber.catalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioFase {
    private Long id;
    private Long servicioId;
    private Integer orden;
    private String nombreFase;
    private Integer duracionMinutos;
    private boolean requiereEmpleado;

    // Domain validation
    public boolean isDuracionValid() {
        return duracionMinutos != null && duracionMinutos > 0;
    }
}
