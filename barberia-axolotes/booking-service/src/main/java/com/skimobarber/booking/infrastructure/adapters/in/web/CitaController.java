package com.skimobarber.booking.infrastructure.adapters.in.web;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.booking.domain.ports.in.CitaUseCase;
import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "Gestión de citas y reservas")
public class CitaController extends BaseController {

    private final CitaUseCase citaUseCase;

    public CitaController(CitaUseCase citaUseCase) {
        this.citaUseCase = citaUseCase;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva cita")
    public ResponseEntity<ApiResponse<Cita>> crear(@RequestBody CrearCitaRequest request) {
        var command = new CitaUseCase.CrearCitaCommand(
            request.clienteId(),
            request.servicioId(),
            request.sucursalId(),
            request.empleadoId(),
            request.fechaHoraInicio()
        );
        return mapResult(citaUseCase.crear(command));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una cita por ID")
    public ResponseEntity<ApiResponse<Cita>> getById(@PathVariable Long id) {
        return mapResult(citaUseCase.findById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener todas las citas de un cliente")
    public ResponseEntity<ApiResponse<List<Cita>>> getByClienteId(@PathVariable Long clienteId) {
        return mapResult(citaUseCase.findByClienteId(clienteId));
    }

    @GetMapping("/sucursal/{sucursalId}")
    @Operation(summary = "Obtener citas de una sucursal en una fecha específica")
    public ResponseEntity<ApiResponse<List<Cita>>> getBySucursalAndFecha(
            @PathVariable Long sucursalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        return mapResult(citaUseCase.findBySucursalIdAndFecha(sucursalId, fecha));
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar una cita")
    public ResponseEntity<ApiResponse<Cita>> cancelar(@PathVariable Long id) {
        return mapResult(citaUseCase.cancelar(id));
    }

    @PostMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar una cita (cambiar estado a EN_CURSO)")
    public ResponseEntity<ApiResponse<Cita>> iniciar(@PathVariable Long id) {
        return mapResult(citaUseCase.iniciar(id));
    }

    @PostMapping("/{id}/completar")
    @Operation(summary = "Completar una cita")
    public ResponseEntity<ApiResponse<Cita>> completar(@PathVariable Long id) {
        return mapResult(citaUseCase.completar(id));
    }

    // Request DTO
    public record CrearCitaRequest(
        Long clienteId,
        Long servicioId,
        Long sucursalId,
        Long empleadoId,
        LocalDateTime fechaHoraInicio
    ) {}
}
