package com.skimobarber.booking.application.service;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.booking.domain.ports.in.HorarioEmpleadoUseCase;
import com.skimobarber.booking.domain.ports.out.IHorarioEmpleadoRepository;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.common.domain.FailureCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioEmpleadoServiceTest {

    @Mock
    private IHorarioEmpleadoRepository repository;

    @InjectMocks
    private HorarioEmpleadoService service;

    @Test
    void crear_exitoso() {
        when(repository.findByEmpleadoIdAndDiaSemana(1L, DayOfWeek.MONDAY)).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(inv -> {
            HorarioEmpleado h = inv.getArgument(0);
            h.setId(10L);
            return h;
        });

        var result = service.crear(new HorarioEmpleadoUseCase.CrearHorarioCommand(
            1L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)
        ));

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertNotNull(result.value().getId());
        verify(repository).save(any());
    }

    @Test
    void crear_conflictoHorarioExistente() {
        when(repository.findByEmpleadoIdAndDiaSemana(1L, DayOfWeek.MONDAY))
            .thenReturn(Optional.of(new HorarioEmpleado()));

        var result = service.crear(new HorarioEmpleadoUseCase.CrearHorarioCommand(
            1L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)
        ));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.CONFLICT, result.failureCategory());
        verify(repository, never()).save(any());
    }

    @Test
    void crear_horasInvalidas() {
        var result = service.crear(new HorarioEmpleadoUseCase.CrearHorarioCommand(
            1L, DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(9, 0)
        ));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void update_exitoso() {
        HorarioEmpleado existing = new HorarioEmpleado(5L, 1L, DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(10,0));
        when(repository.findById(5L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = service.update(5L, new HorarioEmpleadoUseCase.UpdateHorarioCommand(
            LocalTime.of(8, 0), LocalTime.of(12, 0)
        ));

        assertTrue(result.isSuccess());
        assertEquals(LocalTime.of(8,0), result.value().getHoraInicio());
        assertEquals(LocalTime.of(12,0), result.value().getHoraFin());
    }

    @Test
    void update_invalido_regresaValidationError() {
        HorarioEmpleado existing = new HorarioEmpleado(5L, 1L, DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(10,0));
        when(repository.findById(5L)).thenReturn(Optional.of(existing));

        var result = service.update(5L, new HorarioEmpleadoUseCase.UpdateHorarioCommand(
            LocalTime.of(12, 0), LocalTime.of(8, 0)
        ));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        verify(repository, never()).save(any());
    }

    @Test
    void update_noEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        var result = service.update(99L, new HorarioEmpleadoUseCase.UpdateHorarioCommand(
            LocalTime.of(9,0), LocalTime.of(10,0)
        ));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void delete_exitoso() {
        when(repository.existsById(7L)).thenReturn(true);

        var result = service.delete(7L);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        verify(repository).deleteById(7L);
    }

    @Test
    void delete_noEncontrado() {
        when(repository.existsById(7L)).thenReturn(false);

        var result = service.delete(7L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        verify(repository, never()).deleteById(any());
    }

    @Test
    void findByEmpleadoId_retornaLista() {
        when(repository.findByEmpleadoId(1L)).thenReturn(List.of(new HorarioEmpleado()));

        var result = service.findByEmpleadoId(1L);

        assertTrue(result.isSuccess());
        assertEquals(1, result.value().size());
    }
}
