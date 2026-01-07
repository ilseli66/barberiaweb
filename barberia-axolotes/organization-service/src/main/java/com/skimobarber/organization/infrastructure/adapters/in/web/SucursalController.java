package com.skimobarber.organization.infrastructure.adapters.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.organization.domain.model.Sucursal;
import com.skimobarber.organization.domain.ports.in.SucursalUseCase;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/sucursales")
public class SucursalController extends BaseController {
    private final SucursalUseCase sucursalUseCase;

    public SucursalController(SucursalUseCase sucursalUseCase) {
        this.sucursalUseCase = sucursalUseCase;
    }

     @PostMapping()
    public ResponseEntity<ApiResponse<Sucursal>> createSucursal(@RequestBody CreateSucursalRequest request) {
        var command = new SucursalUseCase.CreateSucursalCommand(
            request.establecimientoId(),
            request.nombre(),
            request.latitud(),
            request.longitud()
        );

        return mapResult(sucursalUseCase.create(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sucursal>> getSucursalById(@PathVariable Long id) {
        return mapResult(sucursalUseCase.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Sucursal>>> getAllSucursales() {
        return mapResult(sucursalUseCase.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Sucursal>> updateSucursal(@PathVariable Long id, @RequestBody UpdateSucursalRequest request) {
        var command = new SucursalUseCase.UpdateSucursalCommand(
            request.nombre(),
            request.latitud(),
            request.longitud()
        );
        return mapResult(sucursalUseCase.update(id, command));
    }

    @GetMapping("/establecimiento/{id}")
    public ResponseEntity<ApiResponse<List<Sucursal>>> getSucursalesByEstablecimientoId(@PathVariable Long id) {
        return mapResult(sucursalUseCase.findByEstablecimientoId(id));
    }
    

    public record CreateSucursalRequest(
        Long establecimientoId,
        String nombre,
        Double latitud,
        Double longitud
    ){}

    public record UpdateSucursalRequest(
        String nombre,
        Double latitud,
        Double longitud
    ){}
}
