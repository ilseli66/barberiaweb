package com.skimobarber.booking.application.service;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.booking.domain.model.EstadoCita;
import com.skimobarber.booking.domain.ports.in.CitaUseCase;
import com.skimobarber.booking.domain.ports.out.CatalogServiceClient;
import com.skimobarber.booking.domain.ports.out.ICitaAgendaRepository;
import com.skimobarber.booking.domain.ports.out.ICitaRepository;
import com.skimobarber.booking.domain.ports.out.IdentityServiceClient;
import com.skimobarber.booking.domain.ports.out.OrganizationServiceClient;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.common.domain.FailureCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock private ICitaRepository citaRepository;
    @Mock private ICitaAgendaRepository agendaRepository;
    @Mock private CatalogServiceClient catalogClient;
    @Mock private IdentityServiceClient identityClient;
    @Mock private OrganizationServiceClient organizationClient;

    @InjectMocks private CitaService service;

    private LocalDateTime inicio;

    @BeforeEach
    void setUp() {
        inicio = LocalDateTime.of(2026, 1, 10, 10, 0);
    }

    @Test
    void crear_exitoso() {
        when(identityClient.existsCliente(1L)).thenReturn(true);
        when(catalogClient.existsServicio(2L)).thenReturn(true);
        when(organizationClient.existsSucursal(3L)).thenReturn(true);
        when(organizationClient.empleadoPerteneceASucursal(4L, 3L)).thenReturn(true);
        when(catalogClient.getPrecioVigente(2L)).thenReturn(Optional.of(new BigDecimal("150.00")));
        when(catalogClient.getDuracionServicio(2L)).thenReturn(Optional.of(45));
        when(agendaRepository.existsSolapamiento(4L, inicio, inicio.plusMinutes(45))).thenReturn(false);
        when(citaRepository.save(any())).thenAnswer(inv -> {
            Cita c = inv.getArgument(0);
            c.setId(10L);
            return c;
        });

        var result = service.crear(new CitaUseCase.CrearCitaCommand(1L, 2L, 3L, 4L, inicio));

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertNotNull(result.value());
        assertEquals(EstadoCita.PROGRAMADA, result.value().getEstado());
        assertEquals(new BigDecimal("150.00"), result.value().getPrecioCongelado());
        verify(citaRepository).save(any());
        verify(agendaRepository, never()).deleteByCitaId(any());
    }

    @Test
    void crear_conflictoPorSolapamiento() {
        when(identityClient.existsCliente(1L)).thenReturn(true);
        when(catalogClient.existsServicio(2L)).thenReturn(true);
        when(organizationClient.existsSucursal(3L)).thenReturn(true);
        when(organizationClient.empleadoPerteneceASucursal(4L, 3L)).thenReturn(true);
        when(catalogClient.getPrecioVigente(2L)).thenReturn(Optional.of(new BigDecimal("100")));
        when(catalogClient.getDuracionServicio(2L)).thenReturn(Optional.of(30));
        when(agendaRepository.existsSolapamiento(4L, inicio, inicio.plusMinutes(30))).thenReturn(true);

        var result = service.crear(new CitaUseCase.CrearCitaCommand(1L, 2L, 3L, 4L, inicio));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.CONFLICT, result.failureCategory());
        verify(citaRepository, never()).save(any());
    }

    @Test
    void crear_clienteNoExiste() {
        when(identityClient.existsCliente(1L)).thenReturn(false);

        var result = service.crear(new CitaUseCase.CrearCitaCommand(1L, 2L, 3L, 4L, inicio));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        verifyNoInteractions(catalogClient, organizationClient, agendaRepository, citaRepository);
    }

    @Test
    void crear_empleadoNoPerteneceSucursal() {
        when(identityClient.existsCliente(1L)).thenReturn(true);
        when(catalogClient.existsServicio(2L)).thenReturn(true);
        when(organizationClient.existsSucursal(3L)).thenReturn(true);
        when(organizationClient.empleadoPerteneceASucursal(4L, 3L)).thenReturn(false);

        var result = service.crear(new CitaUseCase.CrearCitaCommand(1L, 2L, 3L, 4L, inicio));

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        verify(citaRepository, never()).save(any());
    }

    @Test
    void cancelar_exitoso_eliminaAgenda() {
        Cita cita = new Cita(5L, 1L, 2L, 3L, EstadoCita.PROGRAMADA, BigDecimal.TEN, LocalDateTime.now());
        when(citaRepository.findById(5L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = service.cancelar(5L);

        assertTrue(result.isSuccess());
        assertEquals(EstadoCita.CANCELADA, result.value().getEstado());
        verify(agendaRepository).deleteByCitaId(5L);
    }

    @Test
    void cancelar_estadoInvalido_devuelveValidationError() {
        Cita cita = new Cita(5L, 1L, 2L, 3L, EstadoCita.EN_CURSO, BigDecimal.TEN, LocalDateTime.now());
        when(citaRepository.findById(5L)).thenReturn(Optional.of(cita));

        var result = service.cancelar(5L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        verify(agendaRepository, never()).deleteByCitaId(any());
        verify(citaRepository, never()).save(any());
    }

    @Test
    void iniciar_exitoso() {
        Cita cita = new Cita(6L, 1L, 2L, 3L, EstadoCita.PROGRAMADA, BigDecimal.ONE, LocalDateTime.now());
        when(citaRepository.findById(6L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = service.iniciar(6L);

        assertTrue(result.isSuccess());
        assertEquals(EstadoCita.EN_CURSO, result.value().getEstado());
    }

    @Test
    void completar_exitoso() {
        Cita cita = new Cita(7L, 1L, 2L, 3L, EstadoCita.EN_CURSO, BigDecimal.ONE, LocalDateTime.now());
        when(citaRepository.findById(7L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = service.completar(7L);

        assertTrue(result.isSuccess());
        assertEquals(EstadoCita.COMPLETADA, result.value().getEstado());
    }

    @Test
    void findById_notFound() {
        when(citaRepository.findById(9L)).thenReturn(Optional.empty());

        var result = service.findById(9L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }
}
