package com.skimobarber.catalog.infrastructure.adapters.in.web;

import com.skimobarber.catalog.domain.model.ListaPrecio;
import com.skimobarber.catalog.domain.model.ServicioListaPrecio;
import com.skimobarber.catalog.domain.ports.in.ListaPrecioUseCase;
import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/listas-precio")
@Tag(name = "Listas de Precio", description = "Gestión de listas de precios y asignación de precios a servicios")
public class ListaPrecioController extends BaseController {

    private final ListaPrecioUseCase listaPrecioUseCase;

    public ListaPrecioController(ListaPrecioUseCase listaPrecioUseCase) {
        this.listaPrecioUseCase = listaPrecioUseCase;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva lista de precios")
    public ResponseEntity<ApiResponse<ListaPrecio>> create(@RequestBody CreateListaPrecioRequest request) {
        var command = new ListaPrecioUseCase.CreateListaPrecioCommand(
            request.nombre(),
            request.fechaInicio(),
            request.fechaFin()
        );
        return mapResult(listaPrecioUseCase.create(command));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una lista de precios por ID")
    public ResponseEntity<ApiResponse<ListaPrecio>> getById(@PathVariable Long id) {
        return mapResult(listaPrecioUseCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las listas de precios")
    public ResponseEntity<ApiResponse<List<ListaPrecio>>> getAll() {
        return mapResult(listaPrecioUseCase.findAll());
    }

    @GetMapping("/vigentes")
    @Operation(summary = "Obtener solo las listas de precios vigentes")
    public ResponseEntity<ApiResponse<List<ListaPrecio>>> getVigentes() {
        return mapResult(listaPrecioUseCase.findVigentes());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una lista de precios")
    public ResponseEntity<ApiResponse<ListaPrecio>> update(
            @PathVariable Long id,
            @RequestBody UpdateListaPrecioRequest request) {
        var command = new ListaPrecioUseCase.UpdateListaPrecioCommand(
            request.nombre(),
            request.fechaInicio(),
            request.fechaFin(),
            request.activo()
        );
        return mapResult(listaPrecioUseCase.update(id, command));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una lista de precios")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return mapResult(listaPrecioUseCase.delete(id));
    }


    @GetMapping("/{listaPrecioId}/servicios/{servicioId}/precio")
    @Operation(summary = "Obtener el precio de un servicio en una lista de precios específica")
    public ResponseEntity<ApiResponse<BigDecimal>> getPrecioServicio(
            @PathVariable Long listaPrecioId,
            @PathVariable Long servicioId) {
        return mapResult(listaPrecioUseCase.getPrecioServicio(servicioId, listaPrecioId));
    }

    @PostMapping("/{listaPrecioId}/servicios/{servicioId}/precio")
    @Operation(summary = "Establecer el precio de un servicio en una lista de precios")
    public ResponseEntity<ApiResponse<Void>> setPrecioServicio(
            @PathVariable Long listaPrecioId,
            @PathVariable Long servicioId,
            @RequestBody SetPrecioServicioRequest request) {
        return mapResult(listaPrecioUseCase.setPrecioServicio(servicioId, listaPrecioId, request.precio()));
    }

    @GetMapping("/servicios/{servicioId}/precio-vigente")
    @Operation(summary = "Obtener el precio vigente de un servicio (de la lista de precios activa)")
    public ResponseEntity<ApiResponse<BigDecimal>> getPrecioVigente(@PathVariable Long servicioId) {
        return mapResult(listaPrecioUseCase.getPrecioVigente(servicioId));
    }

    // Request DTOs
    public record CreateListaPrecioRequest(
        String nombre,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
    ) {}

    public record UpdateListaPrecioRequest(
        String nombre,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Boolean activo
    ) {}

    public record SetPrecioServicioRequest(
        BigDecimal precio
    ) {}
}
