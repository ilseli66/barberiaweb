package com.skimobarber.identity.infrastructure.adapters.in.web;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.identity.domain.ports.in.AuthenticateUserUseCase;
import com.skimobarber.identity.domain.ports.in.AuthenticateUserUseCase.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación.
 * Maneja el endpoint de login y generación de tokens.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    /**
     * Endpoint de login que genera tokens JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        var command = new AuthenticateUserUseCase.AuthCommand(
            request.login(),
            request.password()
        );
        return mapResult(authenticateUserUseCase.execute(command));
    }

    /**
     * Request de login.
     */
    public record LoginRequest(String login, String password) {}
}
