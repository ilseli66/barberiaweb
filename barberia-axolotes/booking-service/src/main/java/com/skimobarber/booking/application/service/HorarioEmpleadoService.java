package com.skimobarber.booking.application.service;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.booking.domain.ports.in.HorarioEmpleadoUseCase;
import com.skimobarber.booking.domain.ports.out.IHorarioEmpleadoRepository;
import com.skimobarber.common.domain.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HorarioEmpleadoService implements HorarioEmpleadoUseCase {

    private final IHorarioEmpleadoRepository repository;

    public HorarioEmpleadoService(IHorarioEmpleadoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<HorarioEmpleado> crear(CrearHorarioCommand command) {
        if (command.horaInicio() == null || command.horaFin() == null) {
            return Result.validationError("Las horas de inicio y fin son requeridas");
        }

        if (!command.horaFin().isAfter(command.horaInicio())) {
            return Result.validationError("La hora de fin debe ser posterior a la hora de inicio");
        }

        // Verificar si ya existe un horario para este día
        if (repository.findByEmpleadoIdAndDiaSemana(command.empleadoId(), command.diaSemana()).isPresent()) {
            return Result.conflict("Ya existe un horario para este empleado en el día: " + command.diaSemana());
        }

        HorarioEmpleado horario = new HorarioEmpleado(
            null,
            command.empleadoId(),
            command.diaSemana(),
            command.horaInicio(),
            command.horaFin()
        );

        HorarioEmpleado saved = repository.save(horario);
        return Result.created(saved);
    }

    @Override
    public Result<List<HorarioEmpleado>> findByEmpleadoId(Long empleadoId) {
        return Result.success(repository.findByEmpleadoId(empleadoId));
    }

    @Override
    @Transactional
    public Result<HorarioEmpleado> update(Long id, UpdateHorarioCommand command) {
        return repository.findById(id)
            .map(horario -> {
                if (command.horaInicio() != null) horario.setHoraInicio(command.horaInicio());
                if (command.horaFin() != null) horario.setHoraFin(command.horaFin());
                
                if (!horario.isHorarioValido()) {
                    return Result.<HorarioEmpleado>validationError("Horario inválido");
                }
                
                HorarioEmpleado saved = repository.save(horario);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Horario no encontrado: " + id));
    }

    @Override
    @Transactional
    public Result<Void> delete(Long id) {
        if (!repository.existsById(id)) {
            return Result.notFound("Horario no encontrado: " + id);
        }
        repository.deleteById(id);
        return Result.noContent();
    }
}
