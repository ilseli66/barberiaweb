package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.identity.domain.enums.TipoRol;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.in.CreateUsuarioUseCase.CreateUsuarioCommand;
import com.skimobarber.identity.domain.ports.out.IPersonaRepository;
import com.skimobarber.identity.domain.ports.out.IUsuarioRepository;
import com.skimobarber.identity.domain.ports.out.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUsuarioServiceTest {

    @Mock
    private IPersonaRepository personaRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUsuarioService service;

    @Test
    void shouldReturnValidationErrorWhenLoginTooShort() {
        CreateUsuarioCommand command = new CreateUsuarioCommand(
            "Juan", "Perez", "Lopez", "juan@example.com", "555", "usr", "password123", "cliente"
        );

        Result<Usuario> result = service.execute(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertTrue(result.message().contains("login"));
    }

    @Test
    void shouldReturnConflictWhenLoginAlreadyExists() {
        CreateUsuarioCommand command = new CreateUsuarioCommand(
            "Juan", "Perez", "Lopez", "juan@example.com", "555", "usuario1", "password123", "cliente"
        );
        when(usuarioRepository.existsByLogin("usuario1")).thenReturn(true);

        Result<Usuario> result = service.execute(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.CONFLICT, result.failureCategory());
        assertTrue(result.message().contains("login"));
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() {
        CreateUsuarioCommand command = new CreateUsuarioCommand(
            "Juan", "Perez", "Lopez", "juan@example.com", "555", "usuario1", "password123", "cliente"
        );
        when(usuarioRepository.existsByLogin("usuario1")).thenReturn(false);
        when(personaRepository.existsByEmail("juan@example.com")).thenReturn(true);

        Result<Usuario> result = service.execute(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.CONFLICT, result.failureCategory());
        assertTrue(result.message().contains("email"));
    }

    @Test
    void shouldCreateUsuarioWhenDataIsValid() {
        CreateUsuarioCommand command = new CreateUsuarioCommand(
            "Juan", "Perez", "Lopez", "juan@example.com", "555", "usuario1", "password123", "cliente"
        );
        when(usuarioRepository.existsByLogin("usuario1")).thenReturn(false);
        when(personaRepository.existsByEmail("juan@example.com")).thenReturn(false);

        Persona savedPersona = new Persona();
        savedPersona.setId(10L);
        when(personaRepository.save(any(Persona.class))).thenReturn(savedPersona);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-pass");

        Usuario savedUsuario = new Usuario(10L, "usuario1", TipoRol.CLIENTE, true);
        when(usuarioRepository.saveWithPassword(any(Usuario.class), any(String.class)))
            .thenReturn(savedUsuario);

        Result<Usuario> result = service.execute(command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals(savedUsuario, result.value());
        verify(passwordEncoder).encode("password123");
        verify(usuarioRepository).saveWithPassword(any(Usuario.class), any(String.class));
    }
}
