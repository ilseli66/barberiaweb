package com.skimobarber.organization.infrastructure.adapters.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.organization.domain.model.Empleado;
import com.skimobarber.organization.domain.ports.in.EmpleadoUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;




@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController extends BaseController {
    private final EmpleadoUseCase empleadoUseCase;

    public EmpleadoController(EmpleadoUseCase empleadoUseCase) {
        this.empleadoUseCase = empleadoUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Long>> createEmpleado(@RequestBody EmpleadoRequest request) {
        var command = new EmpleadoUseCase.CreateEmpleadoCommand(
            request.personaId(),
            request.sucursalId(),
            request.especialidad()
        );
        
        return mapResult(empleadoUseCase.create(command));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Empleado>> updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoUpdateRequest request) {
        var command = new EmpleadoUseCase.UpdateEmpleadoCommand(
            request.sucursalId(),
            request.especialidad()
        );

        return mapResult(empleadoUseCase.update(id, command));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Empleado>> findById(@RequestParam Long id) {
        var result = empleadoUseCase.findById(id);
        return mapResult(result);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Empleado>>> findAll() {
        var result = empleadoUseCase.findAll();
        return mapResult(result);
    }

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<ApiResponse<List<Empleado>>> findBySucursalId(@PathVariable Long sucursalId) {
        var result = empleadoUseCase.findBySucursalId(sucursalId);
        return mapResult(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmpleado(@PathVariable Long id) {
        var result = empleadoUseCase.delete(id);
        return mapResult(result);
    }
    

    public record EmpleadoRequest(
        Long personaId,
        Long sucursalId,
        String especialidad
    ) {}

    public record EmpleadoUpdateRequest(
        Long sucursalId,
        String especialidad
    ) {}
    
}
