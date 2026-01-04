package com.skimobarber.catalog.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.skimobarber.catalog.domain.model.Servicio;

public interface ServicioRepository {
    Servicio save(Servicio servicio);
    Optional<Servicio> findById(Long id);
    List<Servicio> findAll();
    List<Servicio> findByActivo(boolean activo);
    void deleteById(Long id);
    boolean existsById(Long id);
}
