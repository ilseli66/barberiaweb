package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.identity.domain.enums.TipoRol;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.out.IClienteRepository;
import com.skimobarber.identity.domain.ports.out.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagePuntosFidelidadServiceTest {

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private ManagePuntosFidelidadService service;

    @Test
    void shouldRejectNonPositivePointsOnAgregar() {
        Result<Cliente> result = service.agregarPuntos(1L, 0);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void shouldRejectAgregarWhenUserIsNotCliente() {
        Usuario usuario = new Usuario(1L, "empleado", TipoRol.EMPLEADO, true);
        when(usuarioRepository.findByPersonaId(1L)).thenReturn(Optional.of(usuario));

        Result<Cliente> result = service.agregarPuntos(1L, 5);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void shouldAgregarPuntosWhenClienteExists() {
        Usuario usuario = new Usuario(1L, "cliente", TipoRol.CLIENTE, true);
        Cliente cliente = new Cliente(1L, 10, "");
        when(usuarioRepository.findByPersonaId(1L)).thenReturn(Optional.of(usuario));
        when(clienteRepository.findByPersonaId(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Result<Cliente> result = service.agregarPuntos(1L, 5);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals(15, result.value().getPuntosFidelidad());
    }

    @Test
    void shouldRejectCanjearWhenInsufficientPoints() {
        Cliente cliente = new Cliente(1L, 5, "");
        when(clienteRepository.findByPersonaId(1L)).thenReturn(Optional.of(cliente));

        Result<Cliente> result = service.canjearPuntos(1L, 10);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void shouldReturnNotFoundWhenClienteMissingOnCanjear() {
        when(clienteRepository.findByPersonaId(1L)).thenReturn(Optional.empty());

        Result<Cliente> result = service.canjearPuntos(1L, 5);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
    }

    @Test
    void shouldCanjearPuntosWhenSufficient() {
        Cliente cliente = new Cliente(1L, 10, "");
        when(clienteRepository.findByPersonaId(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Result<Cliente> result = service.canjearPuntos(1L, 5);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertEquals(5, result.value().getPuntosFidelidad());
    }
}
