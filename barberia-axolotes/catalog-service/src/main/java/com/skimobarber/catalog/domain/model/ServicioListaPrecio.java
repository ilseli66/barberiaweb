package com.skimobarber.catalog.domain.model;

import java.math.BigDecimal;

/**
 * Asociación entre un servicio y una lista de precios
 */
public class ServicioListaPrecio {
    private Long servicioId;
    private Long listaPrecioId;
    private BigDecimal precio;

    public ServicioListaPrecio() {}

    public ServicioListaPrecio(Long servicioId, Long listaPrecioId, BigDecimal precio) {
        this.servicioId = servicioId;
        this.listaPrecioId = listaPrecioId;
        this.precio = precio;
    }

    // Domain validation
    public boolean isPrecioValid() {
        return precio != null && precio.compareTo(BigDecimal.ZERO) > 0;
    }

    // Getters and Setters
    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public Long getListaPrecioId() { return listaPrecioId; }
    public void setListaPrecioId(Long listaPrecioId) { this.listaPrecioId = listaPrecioId; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}
