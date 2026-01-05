package com.skimobarber.catalog.application.service;

import com.skimobarber.catalog.domain.model.Servicio;
import com.skimobarber.catalog.domain.ports.in.ServicioUseCase.CreateServicioCommand;
import com.skimobarber.catalog.domain.ports.in.ServicioUseCase.UpdateServicioCommand;
import com.skimobarber.catalog.domain.ports.out.IServicioRepository;
import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
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
class ServicioServiceTest {

    @Mock
    private IServicioRepository repository;

    @InjectMocks
    private ServicioService service;

    @Test
    void shouldReturnValidationErrorWhenNombreBlank() {
        CreateServicioCommand command = new CreateServicioCommand(" ", "desc");

        Result<Servicio> result = service.create(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertTrue(result.message().contains("nombre"));
    }

    @Test
    void shouldCreateServicioWithNombre() {
        CreateServicioCommand command = new CreateServicioCommand("Corte", "Corte de cabello");
        Servicio saved = new Servicio(1L, "Corte", "Corte de cabello", true);
        when(repository.save(any(Servicio.class))).thenReturn(saved);

        Result<Servicio> result = service.create(command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals("Corte", result.value().getNombre());
        assertTrue(result.value().isActivo());
    }

    @Test
    void shouldReturnNotFoundOnFindByIdMissing() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Result<Servicio> result = service.findById(1L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldReturnServicioOnFindById() {
        Servicio servicio = new Servicio(1L, "Corte", "Corte de cabello", true);
        when(repository.findById(1L)).thenReturn(Optional.of(servicio));

        Result<Servicio> result = service.findById(1L);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals("Corte", result.value().getNombre());
    }

    @Test
    void shouldReturnAllServicios() {
        Servicio s1 = new Servicio(1L, "Corte", "desc", true);
        Servicio s2 = new Servicio(2L, "Tinte", "desc", true);
        when(repository.findAll()).thenReturn(java.util.List.of(s1, s2));

        Result<java.util.List<Servicio>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.value().size());
    }

    @Test
    void shouldReturnOnlyActivosServicios() {
        Servicio s1 = new Servicio(1L, "Corte", "desc", true);
        when(repository.findByActivo(true)).thenReturn(java.util.List.of(s1));

        Result<java.util.List<Servicio>> result = service.findActivos();

        assertTrue(result.isSuccess());
        assertEquals(1, result.value().size());
        assertTrue(result.value().get(0).isActivo());
    }

    @Test
    void shouldReturnNotFoundOnUpdateMissing() {
        UpdateServicioCommand command = new UpdateServicioCommand("Nuevo", "desc");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Result<Servicio> result = service.update(1L, command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldUpdateServicio() {
        UpdateServicioCommand command = new UpdateServicioCommand("Peinado", "Peinado profesional");
        Servicio existing = new Servicio(1L, "Corte", "desc", true);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Servicio.class))).thenAnswer(inv -> inv.getArgument(0));

        Result<Servicio> result = service.update(1L, command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals("Peinado", result.value().getNombre());
        assertEquals("Peinado profesional", result.value().getDescripcion());
    }

    @Test
    void shouldActivarServicio() {
        Servicio servicio = new Servicio(1L, "Corte", "desc", false);
        when(repository.findById(1L)).thenReturn(Optional.of(servicio));
        when(repository.save(any(Servicio.class))).thenAnswer(inv -> inv.getArgument(0));

        Result<Servicio> result = service.activar(1L);

        assertTrue(result.isSuccess());
        assertTrue(result.value().isActivo());
    }

    @Test
    void shouldDesactivarServicio() {
        Servicio servicio = new Servicio(1L, "Corte", "desc", true);
        when(repository.findById(1L)).thenReturn(Optional.of(servicio));
        when(repository.save(any(Servicio.class))).thenAnswer(inv -> inv.getArgument(0));

        Result<Servicio> result = service.desactivar(1L);

        assertTrue(result.isSuccess());
        assertFalse(result.value().isActivo());
    }

    @Test
    void shouldReturnNotFoundOnActivarMissing() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Result<Servicio> result = service.activar(1L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldReturnNotFoundOnDesactivarMissing() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Result<Servicio> result = service.desactivar(1L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldReturnNotFoundOnDeleteMissing() {
        when(repository.existsById(1L)).thenReturn(false);

        Result<Void> result = service.delete(1L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldDeleteServicio() {
        when(repository.existsById(1L)).thenReturn(true);

        Result<Void> result = service.delete(1L);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        verify(repository).deleteById(1L);
    }
}
