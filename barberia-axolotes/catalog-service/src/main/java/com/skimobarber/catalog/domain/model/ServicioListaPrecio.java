package com.skimobarber.catalog.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioListaPrecio {
    private Long servicioId;
    private Long listaPrecioId;
    private BigDecimal precio;

    // Domain validation
    public boolean isPrecioValid() {
        return precio != null && precio.compareTo(BigDecimal.ZERO) > 0;
    }
}
