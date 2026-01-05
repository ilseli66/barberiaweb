package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.enums.TipoRol;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.in.AuthenticateUserUseCase.AuthCommand;
import com.skimobarber.identity.domain.ports.in.AuthenticateUserUseCase.AuthResponse;
import com.skimobarber.identity.domain.ports.out.CredentialsVerifier;
import com.skimobarber.identity.domain.ports.out.TokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserServiceTest {

    @Mock
    private CredentialsVerifier credentialsVerifier;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthenticateUserService service;

    @Test
    void shouldReturnValidationErrorWhenLoginBlank() {
        Result<?> result = service.execute(new AuthCommand(" ", "secret"));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertTrue(result.message().contains("login"));
    }

    @Test
    void shouldReturnValidationErrorWhenPasswordBlank() {
        Result<?> result = service.execute(new AuthCommand("user", ""));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertTrue(result.message().contains("password"));
    }

    @Test
    void shouldReturnUnauthorizedWhenCredentialsInvalid() {
        when(credentialsVerifier.verifyCredentials("user", "bad"))
            .thenReturn(Optional.empty());

        Result<?> result = service.execute(new AuthCommand("user", "bad"));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.UNAUTHORIZED, result.failureCategory());
        assertTrue(result.message().contains("Credenciales"));
    }

    @Test
    void shouldReturnTokensWhenCredentialsValid() {
        Usuario usuario = new Usuario(5L, "user", TipoRol.CLIENTE, true);
        when(credentialsVerifier.verifyCredentials("user", "good"))
            .thenReturn(Optional.of(usuario));
        when(tokenProvider.generateAccessToken(usuario)).thenReturn("access-token");
        when(tokenProvider.generateRefreshToken(usuario)).thenReturn("refresh-token");
        when(tokenProvider.getExpirationSeconds()).thenReturn(3600L);

        Result<?> result = service.execute(new AuthCommand("user", "good"));

        assertTrue(result.isSuccess());
        assertNotNull(result.value());
        AuthResponse response = (AuthResponse) result.value();
        assertEquals("access-token", response.accessToken());
        assertEquals("refresh-token", response.refreshToken());
        assertEquals("user", response.login());
        assertEquals(TipoRol.CLIENTE.name(), response.role());
    }
}
