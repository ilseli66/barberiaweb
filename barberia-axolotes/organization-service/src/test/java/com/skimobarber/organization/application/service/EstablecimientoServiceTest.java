package com.skimobarber.organization.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.organization.domain.model.Establecimiento;
import com.skimobarber.organization.domain.ports.out.IEstablecimientoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstablecimientoServiceTest {

    @Mock
    private IEstablecimientoRepository repository;

    @InjectMocks
    private EstablecimientoService service;

    @Test
    void shouldFailWhenNombreBlank() {
        Result<Establecimiento> result = service.create("   ");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void shouldCreateEstablecimiento() {
        Establecimiento saved = new Establecimiento(1L, "Barber");
        when(repository.save(any(Establecimiento.class))).thenReturn(saved);

        Result<Establecimiento> result = service.create("Barber");

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals(saved, result.value());
    }

    @Test
    void shouldReturnNotFoundOnUpdateWhenMissing() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        Result<Establecimiento> result = service.update(9L, "Nuevo");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldUpdateWhenExists() {
        Establecimiento existente = new Establecimiento(2L, "Old");
        when(repository.findById(2L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Establecimiento.class))).thenAnswer(inv -> inv.getArgument(0));

        Result<Establecimiento> result = service.update(2L, "New");

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals("New", result.value().getNombre());
    }

    @Test
    void shouldReturnNotFoundOnDeleteMissing() {
        when(repository.existsById(5L)).thenReturn(false);

        Result<Void> result = service.delete(5L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldDeleteWhenExists() {
        when(repository.existsById(3L)).thenReturn(true);

        Result<Void> result = service.delete(3L);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        verify(repository).deleteById(3L);
    }
}
