package com.skimobarber.catalog.infrastructure.adapters.in.web;

import com.skimobarber.catalog.domain.model.Servicio;
import com.skimobarber.catalog.domain.ports.in.ServicioUseCase;
import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios", description = "Gestión de servicios de la barbería")
public class ServicioController extends BaseController {

    private final ServicioUseCase servicioUseCase;

    public ServicioController(ServicioUseCase servicioUseCase) {
        this.servicioUseCase = servicioUseCase;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo servicio")
    public ResponseEntity<ApiResponse<Servicio>> create(@RequestBody CreateServicioRequest request) {
        var command = new ServicioUseCase.CreateServicioCommand(
            request.nombre(),
            request.descripcion()
        );
        return mapResult(servicioUseCase.create(command));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un servicio por ID")
    public ResponseEntity<ApiResponse<Servicio>> getById(@PathVariable Long id) {
        return mapResult(servicioUseCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los servicios")
    public ResponseEntity<ApiResponse<List<Servicio>>> getAll() {
        return mapResult(servicioUseCase.findAll());
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener solo los servicios activos")
    public ResponseEntity<ApiResponse<List<Servicio>>> getActivos() {
        return mapResult(servicioUseCase.findActivos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un servicio")
    public ResponseEntity<ApiResponse<Servicio>> update(
            @PathVariable Long id,
            @RequestBody UpdateServicioRequest request) {
        var command = new ServicioUseCase.UpdateServicioCommand(
            request.nombre(),
            request.descripcion()
        );
        return mapResult(servicioUseCase.update(id, command));
    }

    @PostMapping("/{id}/activar")
    @Operation(summary = "Activar un servicio")
    public ResponseEntity<ApiResponse<Servicio>> activar(@PathVariable Long id) {
        return mapResult(servicioUseCase.activar(id));
    }

    @PostMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar un servicio")
    public ResponseEntity<ApiResponse<Servicio>> desactivar(@PathVariable Long id) {
        return mapResult(servicioUseCase.desactivar(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un servicio")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return mapResult(servicioUseCase.delete(id));
    }

    // Request DTOs
    public record CreateServicioRequest(
        String nombre,
        String descripcion
    ) {}

    public record UpdateServicioRequest(
        String nombre,
        String descripcion
    ) {}
}
