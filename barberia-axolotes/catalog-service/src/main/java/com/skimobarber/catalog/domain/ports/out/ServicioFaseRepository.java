package com.skimobarber.catalog.domain.ports.out;

import com.skimobarber.catalog.domain.model.ServicioFase;

import java.util.List;
import java.util.Optional;

public interface ServicioFaseRepository {
    ServicioFase save(ServicioFase servicioFase);
    Optional<ServicioFase> findById(Long id);
    List<ServicioFase> findByServicioIdOrderByOrden(Long servicioId);
    void deleteById(Long id);
    boolean existsById(Long id);
}
