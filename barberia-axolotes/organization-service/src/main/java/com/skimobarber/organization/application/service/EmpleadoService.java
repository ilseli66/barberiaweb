package com.skimobarber.organization.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.organization.domain.model.Empleado;
import com.skimobarber.organization.domain.ports.in.EmpleadoUseCase;
import com.skimobarber.organization.domain.ports.out.IEmpleadoRepository;
import com.skimobarber.organization.domain.ports.out.ISucursalRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoService implements EmpleadoUseCase {

    private final IEmpleadoRepository empleadoRepository;
    private final ISucursalRepository sucursalRepository;

    public EmpleadoService(IEmpleadoRepository empleadoRepository,
                           ISucursalRepository sucursalRepository) {
        this.empleadoRepository = empleadoRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    @Transactional
    public Result<Empleado> create(CreateEmpleadoCommand command) {
        if (!sucursalRepository.existsById(command.sucursalId())) {
            return Result.notFound("Sucursal no encontrada con id: " + command.sucursalId());
        }

        if (empleadoRepository.existsByPersonaId(command.personaId())) {
            return Result.conflict("Ya existe un empleado con persona_id: " + command.personaId());
        }

        Empleado empleado = new Empleado(
            command.personaId(),
            command.sucursalId(),
            command.especialidad()
        );

        Empleado saved = empleadoRepository.save(empleado);
        return Result.created(saved);
    }

    @Override
    public Result<Empleado> findById(Long personaId) {
        return empleadoRepository.findByPersonaId(personaId)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Empleado no encontrado con persona_id: " + personaId));
    }

    @Override
    public Result<List<Empleado>> findBySucursalId(Long sucursalId) {
        return Result.success(empleadoRepository.findBySucursalId(sucursalId));
    }

    @Override
    public Result<List<Empleado>> findAll() {
        return Result.success(empleadoRepository.findAll());
    }

    @Override
    @Transactional
    public Result<Empleado> update(Long personaId, UpdateEmpleadoCommand command) {
        return empleadoRepository.findByPersonaId(personaId)
            .map(empleado -> {
                if (command.sucursalId() != null) {
                    if (!sucursalRepository.existsById(command.sucursalId())) {
                        return Result.<Empleado>notFound("Sucursal no encontrada con id: " + command.sucursalId());
                    }
                    empleado.setSucursalId(command.sucursalId());
                }
                if (command.especialidad() != null) empleado.setEspecialidad(command.especialidad());
                Empleado saved = empleadoRepository.save(empleado);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Empleado no encontrado con persona_id: " + personaId));
    }

    @Override
    @Transactional
    public Result<Void> delete(Long personaId) {
        if (!empleadoRepository.existsByPersonaId(personaId)) {
            return Result.notFound("Empleado no encontrado con persona_id: " + personaId);
        }
        empleadoRepository.deleteByPersonaId(personaId);
        return Result.noContent();
    }
}
