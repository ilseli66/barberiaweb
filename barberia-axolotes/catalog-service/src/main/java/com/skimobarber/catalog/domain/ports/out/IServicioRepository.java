package com.skimobarber.catalog.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.skimobarber.catalog.domain.model.Servicio;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

public interface IServicioRepository extends IBaseRepository<Servicio, Long> {
    List<Servicio> findByActivo(boolean activo);
}
