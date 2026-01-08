package com.skimobarber.identity.infrastructure.adapters.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.identity.domain.model.Genero;
import com.skimobarber.identity.domain.ports.in.GeneroUseCase;

@RestController
@RequestMapping("/api/generos")
public class GeneroController extends BaseController {

    private final GeneroUseCase generoUseCase;

    public GeneroController(GeneroUseCase generoUseCase) {
        this.generoUseCase = generoUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Genero>> create(@RequestBody CreateGeneroRequest request) {
        var command = new GeneroUseCase.CreateGeneroCommand(request.nombre());
        return mapResult(generoUseCase.create(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Genero>> getById(@PathVariable Long id) {
        return mapResult(generoUseCase.findById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Genero>>> getAll() {
        return mapResult(generoUseCase.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<Genero>>> getAllActivos() {
        return mapResult(generoUseCase.findAllActivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Genero>> update(
            @PathVariable Long id,
            @RequestBody UpdateGeneroRequest request) {
        var command = new GeneroUseCase.UpdateGeneroCommand(request.nombre(), request.activo());
        return mapResult(generoUseCase.update(id, command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return mapResult(generoUseCase.delete(id));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Genero>> activate(@PathVariable Long id) {
        return mapResult(generoUseCase.activate(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Genero>> deactivate(@PathVariable Long id) {
        return mapResult(generoUseCase.deactivate(id));
    }

    public record CreateGeneroRequest(String nombre) {}

    public record UpdateGeneroRequest(String nombre, boolean activo) {}
}
