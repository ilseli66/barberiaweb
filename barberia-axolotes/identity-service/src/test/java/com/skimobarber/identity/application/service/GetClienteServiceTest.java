package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.ports.out.IClienteRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClienteServiceTest {

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private GetClienteService service;

    // ========== FIND BY ID TESTS ==========

    @Test
    void findById_shouldReturnNotFound_whenClienteDoesNotExist() {
        when(clienteRepository.findByPersonaId(99L)).thenReturn(Optional.empty());

        Result<Cliente> result = service.findById(99L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        assertTrue(result.message().contains("99"));
    }

    @Test
    void findById_shouldReturnCliente_whenExists() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");
        when(clienteRepository.findByPersonaId(1L)).thenReturn(Optional.of(cliente));

        Result<Cliente> result = service.findById(1L);

        assertTrue(result.isSuccess());
        assertEquals(1L, result.value().getPersonaId());
        assertEquals(100, result.value().getPuntosFidelidad());
        assertEquals("Sin alergias", result.value().getNotasAlergias());
    }

    // ========== FIND ALL TESTS ==========

    @Test
    void findAll_shouldReturnEmptyList_whenNoClientesExist() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        Result<List<Cliente>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertTrue(result.value().isEmpty());
    }

    @Test
    void findAll_shouldReturnAllClientes() {
        List<Cliente> clientes = Arrays.asList(
            new Cliente(1L, 100, "Sin alergias"),
            new Cliente(2L, 50, "Alergia a productos con amoníaco"),
            new Cliente(3L, 200, null)
        );
        when(clienteRepository.findAll()).thenReturn(clientes);

        Result<List<Cliente>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(3, result.value().size());
    }

    @Test
    void findAll_shouldReturnClientesWithDifferentPoints() {
        List<Cliente> clientes = Arrays.asList(
            new Cliente(1L, 0, "Nuevo cliente"),
            new Cliente(2L, 500, "Cliente frecuente")
        );
        when(clienteRepository.findAll()).thenReturn(clientes);

        Result<List<Cliente>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.value().size());
        assertEquals(0, result.value().get(0).getPuntosFidelidad());
        assertEquals(500, result.value().get(1).getPuntosFidelidad());
    }
}
