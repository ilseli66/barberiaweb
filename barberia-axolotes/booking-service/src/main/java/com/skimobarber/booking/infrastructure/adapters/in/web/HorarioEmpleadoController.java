package com.skimobarber.booking.infrastructure.adapters.in.web;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.booking.domain.ports.in.HorarioEmpleadoUseCase;
import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@Tag(name = "Horarios de Empleados", description = "Gestión de horarios laborales de empleados")
public class HorarioEmpleadoController extends BaseController {

    private final HorarioEmpleadoUseCase horarioEmpleadoUseCase;

    public HorarioEmpleadoController(HorarioEmpleadoUseCase horarioEmpleadoUseCase) {
        this.horarioEmpleadoUseCase = horarioEmpleadoUseCase;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo horario para un empleado")
    public ResponseEntity<ApiResponse<HorarioEmpleado>> crear(@RequestBody CrearHorarioRequest request) {
        var command = new HorarioEmpleadoUseCase.CrearHorarioCommand(
            request.empleadoId(),
            request.diaSemana(),
            request.horaInicio(),
            request.horaFin()
        );
        return mapResult(horarioEmpleadoUseCase.crear(command));
    }

    @GetMapping("/empleado/{empleadoId}")
    @Operation(summary = "Obtener todos los horarios de un empleado")
    public ResponseEntity<ApiResponse<List<HorarioEmpleado>>> getByEmpleadoId(@PathVariable Long empleadoId) {
        return mapResult(horarioEmpleadoUseCase.findByEmpleadoId(empleadoId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un horario")
    public ResponseEntity<ApiResponse<HorarioEmpleado>> update(
            @PathVariable Long id,
            @RequestBody UpdateHorarioRequest request) {
        var command = new HorarioEmpleadoUseCase.UpdateHorarioCommand(
            request.horaInicio(),
            request.horaFin()
        );
        return mapResult(horarioEmpleadoUseCase.update(id, command));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un horario")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return mapResult(horarioEmpleadoUseCase.delete(id));
    }

    // Request DTOs
    public record CrearHorarioRequest(
        Long empleadoId,
        DayOfWeek diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin
    ) {}

    public record UpdateHorarioRequest(
        LocalTime horaInicio,
        LocalTime horaFin
    ) {}
}
