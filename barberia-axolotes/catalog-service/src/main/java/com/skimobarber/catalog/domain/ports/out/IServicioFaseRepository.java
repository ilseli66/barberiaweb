package com.skimobarber.catalog.domain.ports.out;

import java.util.List;

import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

public interface IServicioFaseRepository extends IBaseRepository<ServicioFase, Long> {
    List<ServicioFase> findByServicioIdOrderByOrden(Long servicioId);
}
