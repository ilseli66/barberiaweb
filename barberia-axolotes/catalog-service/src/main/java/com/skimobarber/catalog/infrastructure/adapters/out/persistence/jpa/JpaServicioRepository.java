package com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa;

import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaServicioRepository extends JpaRepository<ServicioEntity, Long> {
    List<ServicioEntity> findByActivo(boolean activo);
}
