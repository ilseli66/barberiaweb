package com.skimobarber.identity.infrastructure.adapters.in.web;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.ports.in.CreateClienteUseCase;
import com.skimobarber.identity.domain.ports.in.GetClienteUseCase;
import com.skimobarber.identity.domain.ports.in.ManagePuntosFidelidadUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController extends BaseController {

    private final CreateClienteUseCase createClienteUseCase;
    private final GetClienteUseCase getClienteUseCase;
    private final ManagePuntosFidelidadUseCase managePuntosFidelidadUseCase;

    public ClienteController(CreateClienteUseCase createClienteUseCase,
                              GetClienteUseCase getClienteUseCase,
                              ManagePuntosFidelidadUseCase managePuntosFidelidadUseCase) {
        this.createClienteUseCase = createClienteUseCase;
        this.getClienteUseCase = getClienteUseCase;
        this.managePuntosFidelidadUseCase = managePuntosFidelidadUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> create(@RequestBody CreateClienteRequest request) {
        var command = new CreateClienteUseCase.CreateClienteCommand(
            request.nombre(),
            request.primerApellido(),
            request.segundoApellido(),
            request.email(),
            request.telefono(),
            request.notasAlergias()
        );
        return mapResult(createClienteUseCase.execute(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> getById(@PathVariable Long id) {
        return mapResult(getClienteUseCase.findById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> getAll() {
        return mapResult(getClienteUseCase.findAll());
    }

    @PostMapping("/{id}/puntos/agregar")
    public ResponseEntity<ApiResponse<Cliente>> agregarPuntos(
            @PathVariable Long id,
            @RequestParam int puntos) {
        return mapResult(managePuntosFidelidadUseCase.agregarPuntos(id, puntos));
    }

    @PostMapping("/{id}/puntos/canjear")
    public ResponseEntity<ApiResponse<Cliente>> canjearPuntos(
            @PathVariable Long id,
            @RequestParam int puntos) {
        return mapResult(managePuntosFidelidadUseCase.canjearPuntos(id, puntos));
    }

    public record CreateClienteRequest(
        String nombre,
        String primerApellido,
        String segundoApellido,
        String email,
        String telefono,
        String notasAlergias
    ) {}
}
