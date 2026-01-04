package com.skimobarber.organization.domain.ports.out;

import com.skimobarber.organization.domain.model.Establecimiento;

import java.util.List;
import java.util.Optional;

public interface EstablecimientoRepository {
    Establecimiento save(Establecimiento establecimiento);
    Optional<Establecimiento> findById(Long id);
    List<Establecimiento> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
