package com.skimobarber.organization.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.organization.domain.model.Establecimiento;
import com.skimobarber.organization.domain.ports.in.EstablecimientoUseCase;
import com.skimobarber.organization.domain.ports.out.IEstablecimientoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstablecimientoService implements EstablecimientoUseCase {

    private final IEstablecimientoRepository repository;

    public EstablecimientoService(IEstablecimientoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<Establecimiento> create(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return Result.validationError("El nombre del establecimiento es requerido");
        }

        Establecimiento establecimiento = new Establecimiento(null, nombre);
        Establecimiento saved = repository.save(establecimiento);
        return Result.created(saved);
    }

    @Override
    public Result<Establecimiento> findById(Long id) {
        return repository.findById(id)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Establecimiento no encontrado con id: " + id));
    }

    @Override
    public Result<List<Establecimiento>> findAll() {
        return Result.success(repository.findAll());
    }

    @Override
    @Transactional
    public Result<Establecimiento> update(Long id, String nombre) {
        return repository.findById(id)
            .map(establecimiento -> {
                establecimiento.setNombre(nombre);
                Establecimiento saved = repository.save(establecimiento);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Establecimiento no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public Result<Void> delete(Long id) {
        if (!repository.existsById(id)) {
            return Result.notFound("Establecimiento no encontrado con id: " + id);
        }
        repository.deleteById(id);
        return Result.noContent();
    }
}
