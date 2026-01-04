package com.skimobarber.organization.domain.ports.out;

import com.skimobarber.organization.domain.model.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository {
    Sucursal save(Sucursal sucursal);
    Optional<Sucursal> findById(Long id);
    List<Sucursal> findByEstablecimientoId(Long establecimientoId);
    List<Sucursal> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
