package com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa;

import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioListaPrecioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface JpaServicioListaPrecioRepository extends JpaRepository<ServicioListaPrecioEntity, ServicioListaPrecioEntity.PK> {
    @Query("SELECT slp.precio FROM ServicioListaPrecioEntity slp WHERE slp.servicioId = :servicioId AND slp.listaPrecioId = :listaPrecioId")
    Optional<BigDecimal> findPrecio(@Param("servicioId") Long servicioId, @Param("listaPrecioId") Long listaPrecioId);

    @Query("SELECT slp FROM ServicioListaPrecioEntity slp JOIN ListaPrecioEntity lp ON slp.listaPrecioId = lp.id WHERE slp.servicioId = :servicioId AND lp.activo = true AND lp.fechaInicio <= CURRENT_TIMESTAMP AND (lp.fechaFin IS NULL OR lp.fechaFin >= CURRENT_TIMESTAMP) ORDER BY lp.fechaInicio DESC")
    Optional<ServicioListaPrecioEntity> findPrecioVigente(@Param("servicioId") Long servicioId);
}
