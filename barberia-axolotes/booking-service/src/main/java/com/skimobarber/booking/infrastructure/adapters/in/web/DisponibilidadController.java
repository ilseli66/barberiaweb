package com.skimobarber.booking.infrastructure.adapters.in.web;

import com.skimobarber.booking.domain.ports.in.DisponibilidadUseCase;
import com.skimobarber.booking.domain.ports.in.DisponibilidadUseCase.SlotDisponible;
import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
@Tag(name = "Disponibilidad", description = "Consulta de disponibilidad de empleados y sucursales")
public class DisponibilidadController extends BaseController {

    private final DisponibilidadUseCase disponibilidadUseCase;

    public DisponibilidadController(DisponibilidadUseCase disponibilidadUseCase) {
        this.disponibilidadUseCase = disponibilidadUseCase;
    }

    @GetMapping("/empleado/{empleadoId}")
    @Operation(summary = "Obtener slots disponibles de un empleado para una fecha")
    public ResponseEntity<ApiResponse<List<SlotDisponible>>> getDisponibilidadEmpleado(
            @PathVariable Long empleadoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return mapResult(disponibilidadUseCase.getDisponibilidadEmpleado(empleadoId, fecha));
    }

    @GetMapping("/empleado/{empleadoId}/verificar")
    @Operation(summary = "Verificar si un empleado está disponible en un rango de tiempo")
    public ResponseEntity<ApiResponse<Boolean>> isEmpleadoDisponible(
            @PathVariable Long empleadoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return mapResult(disponibilidadUseCase.isEmpleadoDisponible(empleadoId, inicio, fin));
    }
}
