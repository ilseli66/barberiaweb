package com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa;

import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioFaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaServicioFaseRepository extends JpaRepository<ServicioFaseEntity, Long> {
    List<ServicioFaseEntity> findByServicioIdOrderByOrden(Long servicioId);
}
