package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.in.PersonaUseCase.CreatePersonaCommand;
import com.skimobarber.identity.domain.ports.in.PersonaUseCase.UpdatePersonaCommand;
import com.skimobarber.identity.domain.ports.out.IPersonaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaServiceTest {

    @Mock
    private IPersonaRepository personaRepository;

    @InjectMocks
    private PersonaService service;

    private CreatePersonaCommand createValidCommand() {
        return new CreatePersonaCommand(
            1L,
            "Juan",
            "García",
            "López",
            LocalDate.of(1990, 5, 15),
            "5551234567",
            "juan@example.com"
        );
    }

    private UpdatePersonaCommand createValidUpdateCommand() {
        return new UpdatePersonaCommand(
            1L,
            "Juan Carlos",
            "García",
            "López",
            LocalDate.of(1990, 5, 15),
            "5551234567",
            "juancarlos@example.com"
        );
    }

    // ========== CREATE TESTS ==========

    @Test
    void create_shouldReturnFailure_whenEmailIsInvalid() {
        CreatePersonaCommand command = new CreatePersonaCommand(
            1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "invalid-email"
        );

        Result<Long> result = service.create(command);

        assertFalse(result.isSuccess());
        assertTrue(result.message().contains("Email"));
    }

    @Test
    void create_shouldReturnFailure_whenEmailIsNull() {
        CreatePersonaCommand command = new CreatePersonaCommand(
            1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", null
        );

        Result<Long> result = service.create(command);

        assertFalse(result.isSuccess());
        assertTrue(result.message().contains("Email"));
    }

    @Test
    void create_shouldReturnFailure_whenEmailIsTooShort() {
        CreatePersonaCommand command = new CreatePersonaCommand(
            1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "a@b"
        );

        Result<Long> result = service.create(command);

        assertFalse(result.isSuccess());
        assertTrue(result.message().contains("Email"));
    }

    @Test
    void create_shouldCreatePersona_whenDataIsValid() {
        CreatePersonaCommand command = createValidCommand();
        Persona savedPersona = new Persona(
            10L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com"
        );
        when(personaRepository.save(any(Persona.class))).thenReturn(savedPersona);

        Result<Long> result = service.create(command);

        assertTrue(result.isSuccess());
        assertEquals(10L, result.value());
        verify(personaRepository).save(any(Persona.class));
    }

    // ========== FIND BY ID TESTS ==========

    @Test
    void findById_shouldReturnFailure_whenPersonaDoesNotExist() {
        when(personaRepository.findById(99L)).thenReturn(Optional.empty());

        Result<Persona> result = service.findById(99L);

        assertFalse(result.isSuccess());
        assertTrue(result.message().contains("no encontrada"));
    }

    @Test
    void findById_shouldReturnPersona_whenExists() {
        Persona persona = new Persona(
            1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com"
        );
        when(personaRepository.findById(1L)).thenReturn(Optional.of(persona));

        Result<Persona> result = service.findById(1L);

        assertTrue(result.isSuccess());
        assertEquals("Juan", result.value().getNombre());
        assertEquals("García", result.value().getPrimerApellido());
    }

    // ========== FIND ALL TESTS ==========

    @Test
    void findAll_shouldReturnEmptyList_whenNoPersonasExist() {
        when(personaRepository.findAll()).thenReturn(Collections.emptyList());

        Result<List<Persona>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertTrue(result.value().isEmpty());
    }

    @Test
    void findAll_shouldReturnAllPersonas() {
        List<Persona> personas = Arrays.asList(
            new Persona(1L, 1L, "Juan", "García", "López",
                LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com"),
            new Persona(2L, 2L, "María", "Pérez", "Sánchez",
                LocalDate.of(1985, 3, 20), "5559876543", "maria@example.com")
        );
        when(personaRepository.findAll()).thenReturn(personas);

        Result<List<Persona>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.value().size());
    }

    // ========== UPDATE TESTS ==========

    @Test
    void update_shouldReturnFailure_whenPersonaDoesNotExist() {
        when(personaRepository.findById(99L)).thenReturn(Optional.empty());
        UpdatePersonaCommand command = createValidUpdateCommand();

        Result<Persona> result = service.update(99L, command);

        assertFalse(result.isSuccess());
        assertTrue(result.message().contains("no encontrada"));
    }

    @Test
    void update_shouldUpdatePersona_whenDataIsValid() {
        Persona existingPersona = new Persona(
            1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com"
        );
        when(personaRepository.findById(1L)).thenReturn(Optional.of(existingPersona));
        
        Persona updatedPersona = new Persona(
            1L, 1L, "Juan Carlos", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juancarlos@example.com"
        );
        when(personaRepository.save(any(Persona.class))).thenReturn(updatedPersona);
        
        UpdatePersonaCommand command = createValidUpdateCommand();

        Result<Persona> result = service.update(1L, command);

        assertTrue(result.isSuccess());
        assertEquals("Juan Carlos", result.value().getNombre());
        verify(personaRepository).save(any(Persona.class));
    }

    // ========== DELETE TESTS ==========

    @Test
    void delete_shouldReturnFailure_whenPersonaDoesNotExist() {
        when(personaRepository.findById(99L)).thenReturn(Optional.empty());

        Result<Void> result = service.delete(99L);

        assertFalse(result.isSuccess());
        assertTrue(result.message().contains("no encontrada"));
    }

    @Test
    void delete_shouldDeletePersona_whenExists() {
        Persona persona = new Persona(
            1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com"
        );
        when(personaRepository.findById(1L)).thenReturn(Optional.of(persona));
        doNothing().when(personaRepository).deleteById(1L);

        Result<Void> result = service.delete(1L);

        assertTrue(result.isSuccess());
        verify(personaRepository).deleteById(1L);
    }
}
