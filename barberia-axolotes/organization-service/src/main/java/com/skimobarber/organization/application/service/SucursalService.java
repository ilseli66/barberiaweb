package com.skimobarber.organization.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.organization.domain.model.Sucursal;
import com.skimobarber.organization.domain.ports.in.SucursalUseCase;
import com.skimobarber.organization.domain.ports.out.EstablecimientoRepository;
import com.skimobarber.organization.domain.ports.out.SucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SucursalService implements SucursalUseCase {

    private final SucursalRepository sucursalRepository;
    private final EstablecimientoRepository establecimientoRepository;

    public SucursalService(SucursalRepository sucursalRepository,
                           EstablecimientoRepository establecimientoRepository) {
        this.sucursalRepository = sucursalRepository;
        this.establecimientoRepository = establecimientoRepository;
    }

    @Override
    @Transactional
    public Result<Sucursal> create(CreateSucursalCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            return Result.validationError("El nombre de la sucursal es requerido");
        }

        if (!establecimientoRepository.existsById(command.establecimientoId())) {
            return Result.notFound("Establecimiento no encontrado con id: " + command.establecimientoId());
        }

        Sucursal sucursal = new Sucursal(
            null,
            command.establecimientoId(),
            command.nombre(),
            command.latitud(),
            command.longitud()
        );

        Sucursal saved = sucursalRepository.save(sucursal);
        return Result.created(saved);
    }

    @Override
    public Result<Sucursal> findById(Long id) {
        return sucursalRepository.findById(id)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Sucursal no encontrada con id: " + id));
    }

    @Override
    public Result<List<Sucursal>> findByEstablecimientoId(Long establecimientoId) {
        return Result.success(sucursalRepository.findByEstablecimientoId(establecimientoId));
    }

    @Override
    public Result<List<Sucursal>> findAll() {
        return Result.success(sucursalRepository.findAll());
    }

    @Override
    @Transactional
    public Result<Sucursal> update(Long id, UpdateSucursalCommand command) {
        return sucursalRepository.findById(id)
            .map(sucursal -> {
                if (command.nombre() != null) sucursal.setNombre(command.nombre());
                if (command.latitud() != null) sucursal.setLatitud(command.latitud());
                if (command.longitud() != null) sucursal.setLongitud(command.longitud());
                Sucursal saved = sucursalRepository.save(sucursal);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Sucursal no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public Result<Void> delete(Long id) {
        if (!sucursalRepository.existsById(id)) {
            return Result.notFound("Sucursal no encontrada con id: " + id);
        }
        sucursalRepository.deleteById(id);
        return Result.noContent();
    }
}
