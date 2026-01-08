package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.identity.domain.model.Genero;
import com.skimobarber.identity.domain.ports.in.GeneroUseCase.CreateGeneroCommand;
import com.skimobarber.identity.domain.ports.in.GeneroUseCase.UpdateGeneroCommand;
import com.skimobarber.identity.domain.ports.out.IGeneroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneroServiceTest {

    @Mock
    private IGeneroRepository generoRepository;

    @InjectMocks
    private GeneroService service;

    // ========== CREATE TESTS ==========

    @Test
    void create_shouldReturnValidationError_whenNombreIsNull() {
        CreateGeneroCommand command = new CreateGeneroCommand(null);

        Result<Genero> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertTrue(result.message().contains("nombre"));
    }

    @Test
    void create_shouldReturnValidationError_whenNombreIsBlank() {
        CreateGeneroCommand command = new CreateGeneroCommand("   ");

        Result<Genero> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertTrue(result.message().contains("nombre"));
    }

    @Test
    void create_shouldCreateGenero_whenNombreIsValid() {
        CreateGeneroCommand command = new CreateGeneroCommand("Masculino");
        Genero savedGenero = new Genero(1L, "Masculino", true);
        when(generoRepository.save(any(Genero.class))).thenReturn(savedGenero);

        Result<Genero> result = service.create(command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals("Masculino", result.value().getNombre());
        assertTrue(result.value().isActivo());
        verify(generoRepository).save(any(Genero.class));
    }

    // ========== FIND BY ID TESTS ==========

    @Test
    void findById_shouldReturnNotFound_whenGeneroDoesNotExist() {
        when(generoRepository.findById(99L)).thenReturn(Optional.empty());

        Result<Genero> result = service.findById(99L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        assertTrue(result.message().contains("99"));
    }

    @Test
    void findById_shouldReturnGenero_whenExists() {
        Genero genero = new Genero(1L, "Femenino", true);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));

        Result<Genero> result = service.findById(1L);

        assertTrue(result.isSuccess());
        assertEquals("Femenino", result.value().getNombre());
    }

    // ========== FIND ALL TESTS ==========

    @Test
    void findAll_shouldReturnEmptyList_whenNoGenerosExist() {
        when(generoRepository.findAll()).thenReturn(Collections.emptyList());

        Result<List<Genero>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertTrue(result.value().isEmpty());
    }

    @Test
    void findAll_shouldReturnAllGeneros() {
        List<Genero> generos = Arrays.asList(
            new Genero(1L, "Masculino", true),
            new Genero(2L, "Femenino", true),
            new Genero(3L, "No binario", false)
        );
        when(generoRepository.findAll()).thenReturn(generos);

        Result<List<Genero>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(3, result.value().size());
    }

    // ========== FIND ALL ACTIVOS TESTS ==========

    @Test
    void findAllActivos_shouldReturnOnlyActiveGeneros() {
        List<Genero> generos = Arrays.asList(
            new Genero(1L, "Masculino", true),
            new Genero(2L, "Femenino", true),
            new Genero(3L, "No binario", false)
        );
        when(generoRepository.findAll()).thenReturn(generos);

        Result<List<Genero>> result = service.findAllActivos();

        assertTrue(result.isSuccess());
        assertEquals(2, result.value().size());
        assertTrue(result.value().stream().allMatch(Genero::isActivo));
    }

    @Test
    void findAllActivos_shouldReturnEmptyList_whenNoActiveGeneros() {
        List<Genero> generos = Arrays.asList(
            new Genero(1L, "Inactivo1", false),
            new Genero(2L, "Inactivo2", false)
        );
        when(generoRepository.findAll()).thenReturn(generos);

        Result<List<Genero>> result = service.findAllActivos();

        assertTrue(result.isSuccess());
        assertTrue(result.value().isEmpty());
    }

    // ========== UPDATE TESTS ==========

    @Test
    void update_shouldReturnNotFound_whenGeneroDoesNotExist() {
        when(generoRepository.findById(99L)).thenReturn(Optional.empty());
        UpdateGeneroCommand command = new UpdateGeneroCommand("NuevoNombre", true);

        Result<Genero> result = service.update(99L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void update_shouldReturnValidationError_whenNombreIsNull() {
        Genero existingGenero = new Genero(1L, "Masculino", true);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(existingGenero));
        UpdateGeneroCommand command = new UpdateGeneroCommand(null, true);

        Result<Genero> result = service.update(1L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void update_shouldReturnValidationError_whenNombreIsBlank() {
        Genero existingGenero = new Genero(1L, "Masculino", true);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(existingGenero));
        UpdateGeneroCommand command = new UpdateGeneroCommand("  ", true);

        Result<Genero> result = service.update(1L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void update_shouldUpdateGenero_whenDataIsValid() {
        Genero existingGenero = new Genero(1L, "Masculino", true);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(existingGenero));
        Genero updatedGenero = new Genero(1L, "Masculino Actualizado", false);
        when(generoRepository.save(any(Genero.class))).thenReturn(updatedGenero);
        UpdateGeneroCommand command = new UpdateGeneroCommand("Masculino Actualizado", false);

        Result<Genero> result = service.update(1L, command);

        assertTrue(result.isSuccess());
        assertEquals("Masculino Actualizado", result.value().getNombre());
        assertFalse(result.value().isActivo());
    }

    // ========== DELETE TESTS ==========

    @Test
    void delete_shouldReturnNotFound_whenGeneroDoesNotExist() {
        when(generoRepository.findById(99L)).thenReturn(Optional.empty());

        Result<Void> result = service.delete(99L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void delete_shouldDeleteGenero_whenExists() {
        Genero genero = new Genero(1L, "Masculino", true);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        doNothing().when(generoRepository).deleteById(1L);

        Result<Void> result = service.delete(1L);

        assertTrue(result.isSuccess());
        verify(generoRepository).deleteById(1L);
    }

    // ========== ACTIVATE TESTS ==========

    @Test
    void activate_shouldReturnNotFound_whenGeneroDoesNotExist() {
        when(generoRepository.findById(99L)).thenReturn(Optional.empty());

        Result<Genero> result = service.activate(99L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void activate_shouldActivateGenero_whenExists() {
        Genero genero = new Genero(1L, "Masculino", false);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        Genero activatedGenero = new Genero(1L, "Masculino", true);
        when(generoRepository.save(any(Genero.class))).thenReturn(activatedGenero);

        Result<Genero> result = service.activate(1L);

        assertTrue(result.isSuccess());
        assertTrue(result.value().isActivo());
    }

    // ========== DEACTIVATE TESTS ==========

    @Test
    void deactivate_shouldReturnNotFound_whenGeneroDoesNotExist() {
        when(generoRepository.findById(99L)).thenReturn(Optional.empty());

        Result<Genero> result = service.deactivate(99L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void deactivate_shouldDeactivateGenero_whenExists() {
        Genero genero = new Genero(1L, "Masculino", true);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        Genero deactivatedGenero = new Genero(1L, "Masculino", false);
        when(generoRepository.save(any(Genero.class))).thenReturn(deactivatedGenero);

        Result<Genero> result = service.deactivate(1L);

        assertTrue(result.isSuccess());
        assertFalse(result.value().isActivo());
    }
}
