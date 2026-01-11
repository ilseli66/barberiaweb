package com.skimobarber.catalog.infrastructure.adapters.in.web;

import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.catalog.domain.ports.in.ServicioFaseUseCase;
import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Fases de Servicio", description = "Gestión de las fases que componen un servicio")
public class ServicioFaseController extends BaseController {

    private final ServicioFaseUseCase servicioFaseUseCase;

    public ServicioFaseController(ServicioFaseUseCase servicioFaseUseCase) {
        this.servicioFaseUseCase = servicioFaseUseCase;
    }

    @PostMapping("/servicios/{servicioId}/fases")
    @Operation(summary = "Crear una nueva fase para un servicio")
    public ResponseEntity<ApiResponse<ServicioFase>> create(
            @PathVariable Long servicioId,
            @RequestBody CreateServicioFaseRequest request) {
        var command = new ServicioFaseUseCase.CreateServicioFaseCommand(
            servicioId,
            request.orden(),
            request.nombreFase(),
            request.duracionMinutos(),
            request.requiereEmpleado()
        );
        return mapResult(servicioFaseUseCase.create(command));
    }

    @GetMapping("/servicios/{servicioId}/fases")
    @Operation(summary = "Obtener todas las fases de un servicio")
    public ResponseEntity<ApiResponse<List<ServicioFase>>> getByServicioId(@PathVariable Long servicioId) {
        return mapResult(servicioFaseUseCase.findByServicioId(servicioId));
    }

    @GetMapping("/servicios/{servicioId}/duracion-total")
    @Operation(summary = "Calcular la duración total de un servicio sumando sus fases")
    public ResponseEntity<ApiResponse<Integer>> getDuracionTotal(@PathVariable Long servicioId) {
        return mapResult(servicioFaseUseCase.calcularDuracionTotal(servicioId));
    }

    @PutMapping("/fases/{id}")
    @Operation(summary = "Actualizar una fase de servicio")
    public ResponseEntity<ApiResponse<ServicioFase>> update(
            @PathVariable Long id,
            @RequestBody UpdateServicioFaseRequest request) {
        var command = new ServicioFaseUseCase.UpdateServicioFaseCommand(
            request.orden(),
            request.nombreFase(),
            request.duracionMinutos(),
            request.requiereEmpleado()
        );
        return mapResult(servicioFaseUseCase.update(id, command));
    }

    @DeleteMapping("/fases/{id}")
    @Operation(summary = "Eliminar una fase de servicio")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return mapResult(servicioFaseUseCase.delete(id));
    }

    // Request DTOs
    public record CreateServicioFaseRequest(
        Integer orden,
        String nombreFase,
        Integer duracionMinutos,
        Boolean requiereEmpleado
    ) {}

    public record UpdateServicioFaseRequest(
        Integer orden,
        String nombreFase,
        Integer duracionMinutos,
        Boolean requiereEmpleado
    ) {}
}
