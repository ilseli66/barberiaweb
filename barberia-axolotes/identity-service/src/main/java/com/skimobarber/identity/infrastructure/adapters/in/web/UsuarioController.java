package com.skimobarber.identity.infrastructure.adapters.in.web;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.in.CreateUsuarioUseCase;
import com.skimobarber.identity.domain.ports.in.GetUsuarioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController extends BaseController {

    private final CreateUsuarioUseCase createUsuarioUseCase;
    private final GetUsuarioUseCase getUsuarioUseCase;

    public UsuarioController(CreateUsuarioUseCase createUsuarioUseCase,
                              GetUsuarioUseCase getUsuarioUseCase) {
        this.createUsuarioUseCase = createUsuarioUseCase;
        this.getUsuarioUseCase = getUsuarioUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> create(@RequestBody CreateUsuarioRequest request) {
        var command = new CreateUsuarioUseCase.CreateUsuarioCommand(
            request.nombre(),
            request.primerApellido(),
            request.segundoApellido(),
            request.email(),
            request.telefono(),
            request.login(),
            request.password(),
            request.rol()
        );
        return mapResult(createUsuarioUseCase.execute(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> getById(@PathVariable Long id) {
        return mapResult(getUsuarioUseCase.findById(id));
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<ApiResponse<Usuario>> getByLogin(@PathVariable String login) {
        return mapResult(getUsuarioUseCase.findByLogin(login));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> getAll() {
        return mapResult(getUsuarioUseCase.findAll());
    }

    public record CreateUsuarioRequest(
        String nombre,
        String primerApellido,
        String segundoApellido,
        String email,
        String telefono,
        String login,
        String password,
        String rol
    ) {}
}
