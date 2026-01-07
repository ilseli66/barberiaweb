package com.skimobarber.organization.infrastructure.adapters.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.organization.domain.model.Establecimiento;
import com.skimobarber.organization.domain.ports.in.EstablecimientoUseCase;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/establecimientos")
public class EstablecimientoController extends BaseController {
    private final EstablecimientoUseCase establecimientoUseCase;

    public EstablecimientoController(EstablecimientoUseCase establecimientoUseCase) {
        this.establecimientoUseCase = establecimientoUseCase;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Establecimiento>> createEstablecimiento(@RequestBody String nombre) {
        return mapResult(establecimientoUseCase.create(nombre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Establecimiento>> getEstablecimientoById(@PathVariable Long id) {
        return mapResult(establecimientoUseCase.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Establecimiento>>> getAllEstablecimientos() {
        return mapResult(establecimientoUseCase.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Establecimiento>> updateEstablecimiento(@PathVariable Long id, @RequestBody String nombre) {
        return mapResult(establecimientoUseCase.update(id, nombre));
    }

    public record CreateEstablecimientoRequest(String nombre){}
    
}
