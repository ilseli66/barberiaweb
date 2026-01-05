package com.skimobarber.organization.domain.ports.out;

import com.skimobarber.common.domain.ports.out.IBaseRepository;
import com.skimobarber.organization.domain.model.Sucursal;

import java.util.List;

public interface ISucursalRepository extends IBaseRepository<Sucursal, Long> {
    List<Sucursal> findByEstablecimientoId(Long establecimientoId);
}