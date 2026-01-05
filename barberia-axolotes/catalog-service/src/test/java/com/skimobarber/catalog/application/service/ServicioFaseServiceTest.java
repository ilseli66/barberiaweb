package com.skimobarber.catalog.application.service;

import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.catalog.domain.ports.in.ServicioFaseUseCase.CreateServicioFaseCommand;
import com.skimobarber.catalog.domain.ports.in.ServicioFaseUseCase.UpdateServicioFaseCommand;
import com.skimobarber.catalog.domain.ports.out.IServicioFaseRepository;
import com.skimobarber.catalog.domain.ports.out.IServicioRepository;
import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioFaseServiceTest {

    @Mock
    private IServicioFaseRepository faseRepository;

    @Mock
    private IServicioRepository servicioRepository;

    @InjectMocks
    private ServicioFaseService service;

    @Test
    void shouldReturnNotFoundWhenServicioMissing() {
        CreateServicioFaseCommand command = new CreateServicioFaseCommand(99L, 1, "Preparación", 5, false);
        when(servicioRepository.existsById(99L)).thenReturn(false);

        Result<ServicioFase> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldReturnValidationErrorWhenDuracionInvalid() {
        CreateServicioFaseCommand command = new CreateServicioFaseCommand(1L, 1, "Prep", 0, false);
        when(servicioRepository.existsById(1L)).thenReturn(true);

        Result<ServicioFase> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertTrue(result.message().contains("duración"));
    }

    @Test
    void shouldReturnValidationErrorWhenDuracionNegative() {
        CreateServicioFaseCommand command = new CreateServicioFaseCommand(1L, 1, "Prep", -5, false);
        when(servicioRepository.existsById(1L)).thenReturn(true);

        Result<ServicioFase> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void shouldReturnValidationErrorWhenDuracionNull() {
        CreateServicioFaseCommand command = new CreateServicioFaseCommand(1L, 1, "Prep", null, false);
        when(servicioRepository.existsById(1L)).thenReturn(true);

        Result<ServicioFase> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void shouldCreateServicioFase() {
        CreateServicioFaseCommand command = new CreateServicioFaseCommand(1L, 1, "Preparación", 5, false);
        when(servicioRepository.existsById(1L)).thenReturn(true);
        ServicioFase saved = new ServicioFase(1L, 1L, 1, "Preparación", 5, false);
        when(faseRepository.save(any(ServicioFase.class))).thenReturn(saved);

        Result<ServicioFase> result = service.create(command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals("Preparación", result.value().getNombreFase());
        assertEquals(5, result.value().getDuracionMinutos());
    }

    @Test
    void shouldReturnFasesOrderedByOrden() {
        ServicioFase f1 = new ServicioFase(1L, 1L, 1, "Preparación", 5, false);
        ServicioFase f2 = new ServicioFase(2L, 1L, 2, "Corte", 30, true);
        when(faseRepository.findByServicioIdOrderByOrden(1L)).thenReturn(List.of(f1, f2));

        Result<List<ServicioFase>> result = service.findByServicioId(1L);

        assertTrue(result.isSuccess());
        assertEquals(2, result.value().size());
        assertEquals(1, result.value().get(0).getOrden());
        assertEquals(2, result.value().get(1).getOrden());
    }

    @Test
    void shouldReturnNotFoundOnUpdateMissing() {
        UpdateServicioFaseCommand command = new UpdateServicioFaseCommand(2, "Nuevo", 10, true);
        when(faseRepository.findById(1L)).thenReturn(Optional.empty());

        Result<ServicioFase> result = service.update(1L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldUpdateServicioFase() {
        UpdateServicioFaseCommand command = new UpdateServicioFaseCommand(2, "Nuevo", 15, true);
        ServicioFase existing = new ServicioFase(1L, 1L, 1, "Preparación", 5, false);
        when(faseRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(faseRepository.save(any(ServicioFase.class))).thenAnswer(inv -> inv.getArgument(0));

        Result<ServicioFase> result = service.update(1L, command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals(2, result.value().getOrden());
        assertEquals("Nuevo", result.value().getNombreFase());
        assertEquals(15, result.value().getDuracionMinutos());
        assertTrue(result.value().isRequiereEmpleado());
    }

    @Test
    void shouldReturnNotFoundOnDeleteMissing() {
        when(faseRepository.existsById(1L)).thenReturn(false);

        Result<Void> result = service.delete(1L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldDeleteFase() {
        when(faseRepository.existsById(1L)).thenReturn(true);

        Result<Void> result = service.delete(1L);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        verify(faseRepository).deleteById(1L);
    }

    @Test
    void shouldCalculateDuracionTotalWithMultipleFases() {
        ServicioFase f1 = new ServicioFase(1L, 1L, 1, "Preparación", 5, false);
        ServicioFase f2 = new ServicioFase(2L, 1L, 2, "Corte", 30, true);
        ServicioFase f3 = new ServicioFase(3L, 1L, 3, "Acabado", 10, false);
        when(faseRepository.findByServicioIdOrderByOrden(1L)).thenReturn(List.of(f1, f2, f3));

        Result<Integer> result = service.calcularDuracionTotal(1L);

        assertTrue(result.isSuccess());
        assertEquals(45, result.value());
    }

    @Test
    void shouldCalculateDuracionTotalWithEmptyList() {
        when(faseRepository.findByServicioIdOrderByOrden(1L)).thenReturn(List.of());

        Result<Integer> result = service.calcularDuracionTotal(1L);

        assertTrue(result.isSuccess());
        assertEquals(0, result.value());
    }

    @Test
    void shouldCalculateDuracionTotalWithSinglePhase() {
        ServicioFase f1 = new ServicioFase(1L, 1L, 1, "Preparación", 25, false);
        when(faseRepository.findByServicioIdOrderByOrden(1L)).thenReturn(List.of(f1));

        Result<Integer> result = service.calcularDuracionTotal(1L);

        assertTrue(result.isSuccess());
        assertEquals(25, result.value());
    }
}
