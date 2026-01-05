package com.skimobarber.organization.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.organization.domain.model.Sucursal;
import com.skimobarber.organization.domain.ports.in.SucursalUseCase.CreateSucursalCommand;
import com.skimobarber.organization.domain.ports.in.SucursalUseCase.UpdateSucursalCommand;
import com.skimobarber.organization.domain.ports.out.IEstablecimientoRepository;
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
class SucursalServiceTest {

    @Mock
    private ISucursalRepository sucursalRepository;

    @Mock
    private IEstablecimientoRepository establecimientoRepository;

    @InjectMocks
    private SucursalService service;

    @Test
    void shouldFailWhenNombreBlank() {
        CreateSucursalCommand command = new CreateSucursalCommand(1L, " ", 1.0, 2.0);

        Result<Sucursal> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void shouldReturnNotFoundWhenEstablecimientoMissing() {
        CreateSucursalCommand command = new CreateSucursalCommand(10L, "Centro", 1.0, 2.0);
        when(establecimientoRepository.existsById(10L)).thenReturn(false);

        Result<Sucursal> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldCreateSucursal() {
        CreateSucursalCommand command = new CreateSucursalCommand(10L, "Centro", 1.0, 2.0);
        when(establecimientoRepository.existsById(10L)).thenReturn(true);
        Sucursal saved = new Sucursal(1L, 10L, "Centro", 1.0, 2.0, null);
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(saved);

        Result<Sucursal> result = service.create(command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals(saved, result.value());
    }

    @Test
    void shouldReturnNotFoundOnUpdateWhenMissing() {
        UpdateSucursalCommand command = new UpdateSucursalCommand("Nuevo", 5.0, 6.0);
        when(sucursalRepository.findById(5L)).thenReturn(Optional.empty());

        Result<Sucursal> result = service.update(5L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldUpdateSucursal() {
        UpdateSucursalCommand command = new UpdateSucursalCommand("Nuevo", 5.0, 6.0);
        Sucursal existing = new Sucursal(2L, 10L, "Viejo", 1.0, 2.0, null);
        when(sucursalRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(sucursalRepository.save(any(Sucursal.class))).thenAnswer(inv -> inv.getArgument(0));

        Result<Sucursal> result = service.update(2L, command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals("Nuevo", result.value().getNombre());
        assertEquals(5.0, result.value().getLatitud());
        assertEquals(6.0, result.value().getLongitud());
    }

    @Test
    void shouldReturnNotFoundOnDeleteMissing() {
        when(sucursalRepository.existsById(3L)).thenReturn(false);

        Result<Void> result = service.delete(3L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldDeleteSucursal() {
        when(sucursalRepository.existsById(4L)).thenReturn(true);

        Result<Void> result = service.delete(4L);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        verify(sucursalRepository).deleteById(4L);
    }
}
