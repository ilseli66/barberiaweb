package com.skimobarber.organization.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.organization.domain.model.Empleado;
import com.skimobarber.organization.domain.ports.in.EmpleadoUseCase.CreateEmpleadoCommand;
import com.skimobarber.organization.domain.ports.in.EmpleadoUseCase.UpdateEmpleadoCommand;
import com.skimobarber.organization.domain.ports.out.IEmpleadoRepository;
import com.skimobarber.organization.domain.ports.out.ISucursalRepository;
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
class EmpleadoServiceTest {

    @Mock
    private IEmpleadoRepository empleadoRepository;

    @Mock
    private ISucursalRepository sucursalRepository;

    @InjectMocks
    private EmpleadoService service;

    @Test
    void shouldReturnNotFoundWhenSucursalDoesNotExist() {
        CreateEmpleadoCommand command = new CreateEmpleadoCommand(1L, 99L, "cortes");
        when(sucursalRepository.existsById(99L)).thenReturn(false);

        Result<Long> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldReturnConflictWhenPersonaAlreadyExists() {
        CreateEmpleadoCommand command = new CreateEmpleadoCommand(1L, 2L, "cortes");
        when(sucursalRepository.existsById(2L)).thenReturn(true);
        when(empleadoRepository.existsByPersonaId(1L)).thenReturn(true);

        Result<Long> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.CONFLICT, result.failureCategory());
    }

    @Test
    void shouldCreateEmpleado() {
        CreateEmpleadoCommand command = new CreateEmpleadoCommand(1L, 2L, "cortes");
        when(sucursalRepository.existsById(2L)).thenReturn(true);
        when(empleadoRepository.existsByPersonaId(1L)).thenReturn(false);
        Empleado saved = new Empleado(1L, 2L, "cortes");
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(saved);

        Result<Long> result = service.create(command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals(saved.getPersonaId(), result.value());
    }

    @Test
    void shouldReturnNotFoundOnUpdateWhenMissing() {
        UpdateEmpleadoCommand command = new UpdateEmpleadoCommand(5L, "color");
        when(empleadoRepository.findByPersonaId(1L)).thenReturn(Optional.empty());

        Result<Empleado> result = service.update(1L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingToMissingSucursal() {
        UpdateEmpleadoCommand command = new UpdateEmpleadoCommand(5L, "color");
        Empleado empleado = new Empleado(1L, 2L, "cortes");
        when(empleadoRepository.findByPersonaId(1L)).thenReturn(Optional.of(empleado));
        when(sucursalRepository.existsById(5L)).thenReturn(false);

        Result<Empleado> result = service.update(1L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldUpdateEmpleado() {
        UpdateEmpleadoCommand command = new UpdateEmpleadoCommand(5L, "color");
        Empleado empleado = new Empleado(1L, 2L, "cortes");
        when(empleadoRepository.findByPersonaId(1L)).thenReturn(Optional.of(empleado));
        when(sucursalRepository.existsById(5L)).thenReturn(true);
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(inv -> inv.getArgument(0));

        Result<Empleado> result = service.update(1L, command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals(5L, result.value().getSucursalId());
        assertEquals("color", result.value().getEspecialidad());
    }

    @Test
    void shouldReturnNotFoundOnDeleteMissing() {
        when(empleadoRepository.existsByPersonaId(1L)).thenReturn(false);

        Result<Void> result = service.delete(1L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldDeleteEmpleado() {
        when(empleadoRepository.existsByPersonaId(1L)).thenReturn(true);

        Result<Void> result = service.delete(1L);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        verify(empleadoRepository).deleteByPersonaId(1L);
    }

    @Test
    void shouldReturnNotFoundWhenEmpleadoMissingOnFindById() {
        when(empleadoRepository.findByPersonaId(7L)).thenReturn(Optional.empty());

        Result<Empleado> result = service.findById(7L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }
}
