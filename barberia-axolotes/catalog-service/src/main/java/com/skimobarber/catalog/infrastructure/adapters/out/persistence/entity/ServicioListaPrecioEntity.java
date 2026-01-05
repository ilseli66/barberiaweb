package com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicio_lista_precio")
@IdClass(ServicioListaPrecioEntity.PK.class)
public class ServicioListaPrecioEntity {
    @Id
    @Column(name = "servicio_id")
    private Long servicioId;

    @Id
    @Column(name = "lista_precio_id")
    private Long listaPrecioId;

    @Column(nullable = false)
    private BigDecimal precio;

    // Getters y Setters
    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public Long getListaPrecioId() { return listaPrecioId; }
    public void setListaPrecioId(Long listaPrecioId) { this.listaPrecioId = listaPrecioId; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    // PK class for composite key
    public static class PK implements java.io.Serializable {
        private Long servicioId;
        private Long listaPrecioId;
        public PK() {}
        public PK(Long servicioId, Long listaPrecioId) {
            this.servicioId = servicioId;
            this.listaPrecioId = listaPrecioId;
        }
        // equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PK pk = (PK) o;
            return servicioId.equals(pk.servicioId) && listaPrecioId.equals(pk.listaPrecioId);
        }
        @Override
        public int hashCode() {
            return java.util.Objects.hash(servicioId, listaPrecioId);
        }
    }
}
