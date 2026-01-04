package com.skimobarber.catalog.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.skimobarber.catalog.domain.model.ServicioFase;

public interface ServicioFaseRepository {
    ServicioFase save(ServicioFase servicioFase);
    Optional<ServicioFase> findById(Long id);
    List<ServicioFase> findByServicioIdOrderByOrden(Long servicioId);
    void deleteById(Long id);
    boolean existsById(Long id);
}
