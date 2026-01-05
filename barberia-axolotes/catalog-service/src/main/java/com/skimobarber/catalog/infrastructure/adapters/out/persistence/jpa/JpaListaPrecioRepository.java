package com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa;

import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ListaPrecioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface JpaListaPrecioRepository extends JpaRepository<ListaPrecioEntity, Long> {
    @Query("SELECT lp FROM ListaPrecioEntity lp WHERE lp.activo = true AND lp.fechaInicio <= CURRENT_TIMESTAMP AND (lp.fechaFin IS NULL OR lp.fechaFin >= CURRENT_TIMESTAMP)")
    List<ListaPrecioEntity> findVigentes();
}
