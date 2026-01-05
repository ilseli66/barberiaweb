package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.in.AuthenticateUserUseCase;
import com.skimobarber.identity.domain.ports.out.CredentialsVerifier;
import com.skimobarber.identity.domain.ports.out.TokenProvider;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación que implementa el caso de uso de login.
 * Verifica credenciales y genera tokens JWT.
 */
@Service
public class AuthenticateUserService implements AuthenticateUserUseCase {

    private final CredentialsVerifier credentialsVerifier;
    private final TokenProvider tokenProvider;

    public AuthenticateUserService(CredentialsVerifier credentialsVerifier,
                                    TokenProvider tokenProvider) {
        this.credentialsVerifier = credentialsVerifier;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Result<AuthResponse> execute(AuthCommand command) {
        // Validar que los campos no estén vacíos
        if (command.login() == null || command.login().isBlank()) {
            return Result.validationError("El login es requerido");
        }
        
        if (command.password() == null || command.password().isBlank()) {
            return Result.validationError("El password es requerido");
        }

        // Verificar credenciales
        return credentialsVerifier.verifyCredentials(command.login(), command.password())
            .map(this::createAuthResponse)
            .orElseGet(() -> Result.unauthorized("Credenciales inválidas"));
    }

    private Result<AuthResponse> createAuthResponse(Usuario usuario) {
        String accessToken = tokenProvider.generateAccessToken(usuario);
        String refreshToken = tokenProvider.generateRefreshToken(usuario);

        AuthResponse response = new AuthResponse(
            accessToken,
            refreshToken,
            "Bearer",
            tokenProvider.getExpirationSeconds(),
            usuario.getPersonaId(),
            usuario.getLogin(),
            usuario.getRol().name()
        );

        return Result.success(response);
    }
}
