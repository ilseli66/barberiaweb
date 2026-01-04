package com.skimobarber.catalog.application.service;

import com.skimobarber.catalog.domain.model.Servicio;
import com.skimobarber.catalog.domain.ports.in.ServicioUseCase;
import com.skimobarber.catalog.domain.ports.out.ServicioRepository;
import com.skimobarber.common.domain.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioService implements ServicioUseCase {

    private final ServicioRepository repository;

    public ServicioService(ServicioRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<Servicio> create(CreateServicioCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            return Result.validationError("El nombre del servicio es requerido");
        }

        Servicio servicio = new Servicio(null, command.nombre(), command.descripcion(), true);
        Servicio saved = repository.save(servicio);
        return Result.created(saved);
    }

    @Override
    public Result<Servicio> findById(Long id) {
        return repository.findById(id)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Servicio no encontrado con id: " + id));
    }

    @Override
    public Result<List<Servicio>> findAll() {
        return Result.success(repository.findAll());
    }

    @Override
    public Result<List<Servicio>> findActivos() {
        return Result.success(repository.findByActivo(true));
    }

    @Override
    @Transactional
    public Result<Servicio> update(Long id, UpdateServicioCommand command) {
        return repository.findById(id)
            .map(servicio -> {
                if (command.nombre() != null) servicio.setNombre(command.nombre());
                if (command.descripcion() != null) servicio.setDescripcion(command.descripcion());
                Servicio saved = repository.save(servicio);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Servicio no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public Result<Servicio> activar(Long id) {
        return repository.findById(id)
            .map(servicio -> {
                servicio.setActivo(true);
                Servicio saved = repository.save(servicio);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Servicio no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public Result<Servicio> desactivar(Long id) {
        return repository.findById(id)
            .map(servicio -> {
                servicio.setActivo(false);
                Servicio saved = repository.save(servicio);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Servicio no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public Result<Void> delete(Long id) {
        if (!repository.existsById(id)) {
            return Result.notFound("Servicio no encontrado con id: " + id);
        }
        repository.deleteById(id);
        return Result.noContent();
    }
}
