package com.skimobarber.organization.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.organization.domain.model.Establecimiento;

import java.util.List;

public interface EstablecimientoUseCase {
    Result<Establecimiento> create(String nombre);
    Result<Establecimiento> findById(Long id);
    Result<List<Establecimiento>> findAll();
    Result<Establecimiento> update(Long id, String nombre);
    Result<Void> delete(Long id);
}
